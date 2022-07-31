package com.hanghaecloneproject.config.security.filter;

import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.common.error.ErrorMessage;
import com.hanghaecloneproject.common.error.ErrorResponseUtils;
import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.config.security.jwt.JwtUtils;
import com.hanghaecloneproject.config.security.jwt.VerifyResult;
import com.hanghaecloneproject.config.security.jwt.VerifyResult.TokenStatus;
import com.hanghaecloneproject.user.service.UserService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthFilter extends BasicAuthenticationFilter {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public JwtAuthFilter(AuthenticationManager authenticationManager,
          UserService userService, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
          FilterChain chain) throws IOException, ServletException {

        String accessToken = null;
        try {
            accessToken = extractTokenFromHeader(request, HttpHeaders.AUTHORIZATION);
        } catch (IllegalArgumentException e) {
            chain.doFilter(request, response);
            return;
        }

        VerifyResult verifyResult = jwtUtils.verifyToken(accessToken);

        if (verifyResult.getTokenStatus() == TokenStatus.ACCESS) {
//            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
//            if (operations.get(accessToken) != null && (boolean) operations.get(accessToken)) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                      "이미 로그아웃 하셨습니다. 다시 로그인 해 주세요");
//                return;
//            }

            SecurityContextHolder.getContext()
                  .setAuthentication(createSecurityTokenByUsername(verifyResult.getUsername()));
            chain.doFilter(request, response);
            return;
        }

        if (verifyResult.getTokenStatus() == TokenStatus.EXPIRED) {
            String refreshToken = null;
            try {
                refreshToken = extractTokenFromHeader(request, "refresh_token");
            } catch (IllegalArgumentException e) {
                chain.doFilter(request, response);
            }
            // 반드시 만료된 토큰이 있는 상태에서 refresh_token 이 있어야 함.
            VerifyResult refreshTokenVerifyResult = jwtUtils.verifyToken(refreshToken);
            if (refreshTokenVerifyResult.getTokenStatus() == TokenStatus.ACCESS) {
                SecurityContextHolder.getContext()
                      .setAuthentication(reIssueAccessToken(response,
                            refreshTokenVerifyResult.getUsername()));
            } else {
                ErrorResponseUtils.sendError(response,
                      new ErrorMessage(ErrorCode.EXPIRED_TOKEN,
                            "모든 토큰이 만료되었습니다. 다시 로그인해주세요."));
                chain.doFilter(request, response);
            }
        } else {
            ErrorResponseUtils.sendError(response,
                  new ErrorMessage(ErrorCode.INVALID_TOKEN,
                        "유효하지 않은 토큰입니다."));
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken createSecurityTokenByUsername(String username) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
    }

    private UsernamePasswordAuthenticationToken reIssueAccessToken(HttpServletResponse response,
          String username) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
        String reIssueAccessToken = jwtUtils.issueAccessToken(userDetails.getUsername());
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + reIssueAccessToken);

        return new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
    }

    private String extractTokenFromHeader(HttpServletRequest request, String tokenType)
          throws IllegalArgumentException {
        String headerValue = request.getHeader(tokenType);
        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
            throw new IllegalArgumentException("잘못된 토큰 입니다.");
        }
        return headerValue.substring("Bearer ".length());
    }


}
