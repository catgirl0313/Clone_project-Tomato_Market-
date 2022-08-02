package com.hanghaecloneproject.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.common.error.CommonResponse;
import com.hanghaecloneproject.common.error.ErrorResponseUtils;
import com.hanghaecloneproject.config.security.dto.LoginResponseDto;
import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.config.security.jwt.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

    public FormLoginFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
          HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            LoginRequestDto loginDto = objectMapper.readValue(request.getInputStream(),
                  LoginRequestDto.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                  loginDto.getUsername(), loginDto.getPassword());

            return getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            // login handler에서 처리
            throw new RuntimeException("잘못된 로그인 정보입니다.");
        }
    }

    // 성공 시 JWT 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
          HttpServletResponse response, FilterChain chain, Authentication authResult)
          throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String accessToken = jwtUtils.issueAccessToken(userDetails.getUsername());
        String refreshToken = jwtUtils.issueRefreshToken(userDetails.getUsername());

        LoginResponseDto loginResponseDto =
              new LoginResponseDto(accessToken, refreshToken, userDetails);

        response.getOutputStream().write(objectMapper.writeValueAsBytes(loginResponseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
          HttpServletResponse response, AuthenticationException failed)
          throws IOException, ServletException {
        log.info("failed -> {}", failed.toString());
        ErrorResponseUtils.sendError(response, new CommonResponse<>(ErrorCode.BAD_CREDENTIAL, failed.getMessage()));
    }
}
