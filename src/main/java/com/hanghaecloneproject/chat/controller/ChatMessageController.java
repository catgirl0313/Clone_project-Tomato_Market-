package com.hanghaecloneproject.chat.controller;

import com.hanghaecloneproject.chat.dto.ChatMessageForm;
import com.hanghaecloneproject.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ChatMessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;


    @MessageMapping("/chat") // <-/app/chat 목적지에 대한 메시지 처리
    public void sendMessage(ChatMessageForm message) {
        String receiver = message.getReceiver();
        chatMessageService.save(message);
        simpMessagingTemplate.convertAndSend("/queue/" + receiver, message);
    } //simpMessagingTemplate 는 브로커를 설정하지 않은 경우에 주입받아 사용하는거라는데 여기서의 브로커는 RabbitMQ, ActiveMA 등 외부브로커를 말하는건가?

}


//    @MessageMapping("/fleet/{fleetId}/driver/{driverId}")
//    @SendTo("/topic/fleet/{fleetId}")
//    public Simple simple(@DestinationVariable String fleetId, @DestinationVariable String driverId) {
//        return new Simple(fleetId, driverId);

//    @MessageMapping("/chat/send")
//    public void chat(MessageDto.Send message) {
//        messageService.sendMessage(message);
//        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiverId(), message);
//    }
//    클라이언트에서 /app/chat/send 로 메시지를 발행하므로 메시지를 처리하기 위해 MessageController에서
//    @MessageMapping을 이용해 받아준다.
//
//    받은 메시지를 데이터베이스에 저장하기 위해 messageService의 sendMessage 메소드를 호출하고,
//    messagingTemplate의 convertAndSend 메소드를 통해 /topic/chat/수신자ID 를 구독한 유저에게 해당 메시지를 보낸다.

//    @PostMapping("/chat/room")
//    public ResponseEntity<BasicResponse> JoinChatRoom(@RequestBody ChatRoomDto.Request dto) {
//        try {
//            Long roomId = chatRoomService.joinChatRoom(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(new Result<>(roomId));
//        } catch(IllegalStateException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), "400"));
//        }
//    }
//
//    @GetMapping("/chat/room")
//    public ResponseEntity<BasicResponse> getChatRoomList(@RequestParam Long memberId) {
//        return ResponseEntity.ok(new Result<>(chatRoomService.getRoomList(memberId)));
//    }
//
//    밑의 JoinChatRoom 메소드는 방을 생성하거나 이미 채팅방이 있는 경우 방의 id를 반환해준다.
//
//    getChatRoomList에서는 해당 유저의 채팅방 목록을 반환한다.
//
//    이렇게 로직을 구현하면 클라이언트에서 소켓을 연결한 후 /topic/chat/자신의id를 구독하면 자신에게 오는 메시지를 받아 처리할 수 있다.
