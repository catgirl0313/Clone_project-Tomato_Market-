package com.hanghaecloneproject.chat.config;

import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.config.security.jwt.JwtUtils;
import com.hanghaecloneproject.config.security.jwt.VerifyResult;
import com.hanghaecloneproject.config.security.jwt.VerifyResult.TokenStatus;
import com.hanghaecloneproject.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtStompHandler implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtStompHandler(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("CHECK JWT TOKEN BEFORE CONNECT WS");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("accessor -> {}", accessor);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            VerifyResult accessTokenVerifyResult = jwtUtils.verifyToken(
                  accessor.getFirstNativeHeader("Authorization"));
        }
//        else if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
//            log.info("===SUBSCRIBE===");
//            log.info("validate token -> {}", accessor.getFirstNativeHeader("Authorization"));
//            validateToken(accessor);
//        }
//        else if (accessor.getCommand() == StompCommand.SEND) {
//            log.info("===SEND===");
//            log.info("validate token -> {}", accessor.getFirstNativeHeader("Authorization"));
//            UsernamePasswordAuthenticationToken user = validateToken(accessor);
//            SecurityContextHolder.getContext().setAuthentication(user);
//            accessor.setUser(user);
//        }
        else if (accessor.getCommand() == StompCommand.DISCONNECT) {

        }

        return message;
    }

    // TODO interceptor 내부에서 RefreshToken 처리는 어떻게??
    private UsernamePasswordAuthenticationToken validateToken(StompHeaderAccessor accessor) {
        VerifyResult accessTokenVerifyResult = jwtUtils.verifyToken(
              accessor.getFirstNativeHeader("Authorization"));
        if (accessTokenVerifyResult.getTokenStatus() == TokenStatus.EXPIRED) {
            VerifyResult refreshTokenVerifyResult = jwtUtils.verifyToken(
                  accessor.getFirstNativeHeader("refresh_token"));
            if (refreshTokenVerifyResult.getTokenStatus() == TokenStatus.EXPIRED ||
                  refreshTokenVerifyResult.getTokenStatus() == TokenStatus.DENIED) {
                throw new AccessDeniedException("비정상적인 접근입니다. 다시 로그인해주세요");
            }
        } else if (accessTokenVerifyResult.getTokenStatus() == TokenStatus.DENIED) {
            throw new AccessDeniedException("비정상적인 접근입니다. 다시 로그인해주세요");
        }

        log.info("pass Auth JWT Token");

        UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(
              accessTokenVerifyResult.getUsername());
        return new UsernamePasswordAuthenticationToken(
              userDetails,
              null,
              userDetails.getAuthorities());
    }
}
