package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import com.hanghaecloneproject.chat.dto.ChatMessageForm;
import com.hanghaecloneproject.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    @Transactional
    public void save(ChatMessageForm message) { //userService.findUserByEmailMethod(message.getSender())
        ChatMessage chatMessage = new ChatMessage(message.getMessage(), LocalDateTime.now(), chatRoomService.findById(message.getChatRoomId()).get());
        chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
    }


}
