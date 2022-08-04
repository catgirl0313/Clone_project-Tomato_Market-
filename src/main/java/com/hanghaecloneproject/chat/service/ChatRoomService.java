package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import com.hanghaecloneproject.chat.domain.ChatRoom;
import com.hanghaecloneproject.chat.domain.ChatRoomJoin;
import com.hanghaecloneproject.chat.dto.ChatRoomForm;
import com.hanghaecloneproject.chat.repository.ChatMessageRepository;
import com.hanghaecloneproject.chat.repository.ChatRoomJoinRepository;
import com.hanghaecloneproject.chat.repository.ChatRoomRepository;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.repository.TradeReadRepository;
import com.hanghaecloneproject.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final TradeReadRepository tradeReadRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatMessageRepository chatMessageRepository;


    // 신규 채팅방 생성
    @Transactional
    public Long createNewChatRoom(ChatRoomCreateRequestDto dto, User user) {
        // 채팅방 생성
        Trade trade = tradeReadRepository.findById(dto.getTradeId())
              .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        Long sellerId = trade.getUserId();

        ChatRoom chatRoom = new ChatRoom(dto.getTradeId());
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        return savedChatRoom.getId();
    }

    @Transactional(readOnly = true)
    public CheckChatRoomResult isChatRoomAlreadyExist(ChatRoomCreateRequestDto dto, User user) {
        // 이미 채팅방이 있나 확인
        List<ChatRoom> result = chatRoomRepository.findAllByTradeId(dto.getTradeId())
              .stream()
              .filter(chatRoom -> chatRoomJoinRepository.findByUserIdAndChatRoomId(user.getId(),
                          chatRoom.getId())
                    .isPresent())
              .collect(Collectors.toList());

        return new CheckChatRoomResult(!result.isEmpty(),
              !result.isEmpty() ? result.get(0).getId() : null);
    }

    public List<ChatRoomForm> findAllChatRoomsByUser(User user) {
        List<ChatRoom> chatRoomList = chatRoomJoinRepository.findByUserId(user.getId())
              .stream().map(ChatRoomJoin::getChatRoom)
              .collect(Collectors.toList());

        if (!chatRoomList.isEmpty()) {
            List<ChatRoomForm> result = chatRoomList.stream()
                  .map(chatRoom -> {
                      ChatMessage lastMessage = chatMessageRepository.findByChatRoomIdOrderByCreatedAtDesc(
                            chatRoom.getId()).get(0);
                      return new ChatRoomForm(chatRoom.getTradeId(), lastMessage);
                  }).collect(Collectors.toList());
            return result;
        }
        return List.of();
    }
}
