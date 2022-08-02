package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import com.hanghaecloneproject.chat.dto.ChatMessageForm;
import com.hanghaecloneproject.chat.repository.ChatMessageRepository;
import com.hanghaecloneproject.user.domain.User;
import com.hanghaecloneproject.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;

    @Transactional
    public void save(ChatMessageForm message) {
        User user = userRepository.findByUsername(message.getSender())
              .orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        ChatMessage chatMessage = new ChatMessage(message.getMessage(), LocalDateTime.now(),
              chatRoomService.findById(message.getChatRoomId()).get(), user);
        chatMessageRepository.save(chatMessage);
//        noticeService.addMessageNotice(chatMessage.getChatRoom(),chatMessage.getWriter(), message.getReceiver(),chatMessage.getTime());
    }


}
