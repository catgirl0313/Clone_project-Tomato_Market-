package com.hanghaecloneproject.chat.controller;

import com.hanghaecloneproject.chat.dto.ChatMessageForm;
import com.hanghaecloneproject.chat.dto.ChatMessageResponseDto;
import com.hanghaecloneproject.chat.service.ChatMessageService;
import com.hanghaecloneproject.common.error.CommonResponse;
import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import com.hanghaecloneproject.config.security.jwt.JwtUtils;
import com.hanghaecloneproject.config.security.jwt.VerifyResult;
import com.hanghaecloneproject.config.security.jwt.VerifyResult.TokenStatus;
import com.hanghaecloneproject.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    private final JwtUtils jwtUtils;

    @MessageMapping("/sendChat") // <-/app/sendChat 목적지에 대한 메시지 처리
    public ChatMessageResponseDto sendMessage(
          @Payload ChatMessageForm message,
          @Header("Authorization") String jwtToken) {

        VerifyResult verifyResult = jwtUtils.verifyToken(jwtToken);

        if (verifyResult.getTokenStatus() == TokenStatus.ACCESS) {
            UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(
                  verifyResult.getUsername());
            log.info("sendMessage: {}", message);
            log.info("userDetails: {}", userDetails);

            ChatMessageResponseDto responseDto = chatMessageService.save(message,
                  userDetails.getUser());

            simpMessagingTemplate.convertAndSend("/queue/"
                  + message.getChatRoomId(), responseDto);

            return responseDto;
        } else {
            throw new IllegalArgumentException("다시 로그인해주세요");
        }
    }

    @GetMapping("/chat/{chatRoomId}")
    public ResponseEntity<CommonResponse<List<ChatMessageResponseDto>>> showAllMessage(
          @PathVariable Long chatRoomId) {
        List<ChatMessageResponseDto> chatMessageList = chatMessageService.showAllMessage(
              chatRoomId);

        return ResponseEntity.status(HttpStatus.OK)
              .body(new CommonResponse<>(ErrorCode.SUCCESS, "전송 완료!", chatMessageList));
    }
}