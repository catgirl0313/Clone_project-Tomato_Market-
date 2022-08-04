package com.hanghaecloneproject.chat.controller;

import com.hanghaecloneproject.chat.dto.ChatRoomForm;
import com.hanghaecloneproject.chat.service.ChatRoomCreateRequestDto;
import com.hanghaecloneproject.chat.service.ChatRoomJoinService;
import com.hanghaecloneproject.chat.service.ChatRoomService;
import com.hanghaecloneproject.chat.service.CheckChatRoomResult;
import com.hanghaecloneproject.common.error.CommonResponse;
import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinService chatRoomJoinService;

    // 채팅방 생성
    @PostMapping("/chat")
    public ResponseEntity<CommonResponse<Long>> newChat(@RequestBody ChatRoomCreateRequestDto dto,
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CheckChatRoomResult chatRoomAlreadyExist = chatRoomService.isChatRoomAlreadyExist(dto,
              userDetails.getUser());

        log.info("create chatRoom -> tradeId: {}", dto.getTradeId());
        if (chatRoomAlreadyExist.isExist()) {
            Long chatRoomId = chatRoomAlreadyExist.getChatRoomId();

            return ResponseEntity.status(HttpStatus.OK)
                  .body(new CommonResponse<>(ErrorCode.SUCCESS,
                        chatRoomId));
        }

        Long chatRoomId = chatRoomService.createNewChatRoom(dto, userDetails.getUser());

        // 판매자 + 구매자 채팅방으로 접속
        chatRoomJoinService.joinChatRoom(dto, chatRoomId, userDetails.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK)
              .body(new CommonResponse<>(ErrorCode.SUCCESS, chatRoomId));
    }

    @GetMapping("/chat")
    public ResponseEntity<CommonResponse<List<ChatRoomForm>>> findMyChatRoomAll(
          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ChatRoomForm> result = chatRoomService.findAllChatRoomsByUser(
              userDetails.getUser());

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                  .body(new CommonResponse<>(ErrorCode.SUCCESS,
                        "게시글이 없습니다.", result));
        }

        return ResponseEntity.status(HttpStatus.OK)
              .body(new CommonResponse<>(ErrorCode.SUCCESS, result));
    }
}
