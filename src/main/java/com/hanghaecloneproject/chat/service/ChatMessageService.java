package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import com.hanghaecloneproject.chat.domain.ChatRoom;
import com.hanghaecloneproject.chat.dto.ChatMessageForm;
import com.hanghaecloneproject.chat.dto.ChatMessageResponseDto;
import com.hanghaecloneproject.chat.repository.ChatMessageRepository;
import com.hanghaecloneproject.chat.repository.ChatRoomRepository;
import com.hanghaecloneproject.user.domain.User;
import com.hanghaecloneproject.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatMessageResponseDto save(ChatMessageForm message, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId())
              .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다. / 없는 채팅방입니다."));

        ChatMessage chatMessage = new ChatMessage(message.getMessage(), chatRoom, user);

        ChatMessage save = chatMessageRepository.save(chatMessage);
        return new ChatMessageResponseDto(save);
    }

    public List<ChatMessageResponseDto> showAllMessage(Long chatRoomId) {
        return chatMessageRepository
              .findByChatRoomIdOrderByCreatedAtDesc(chatRoomId).stream()
              .map(ChatMessageResponseDto::new)
              .collect(Collectors.toList());
    }
}
