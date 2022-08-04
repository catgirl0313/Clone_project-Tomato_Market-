package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatRoom;
import com.hanghaecloneproject.chat.domain.ChatRoomJoin;
import com.hanghaecloneproject.chat.repository.ChatRoomJoinRepository;
import com.hanghaecloneproject.chat.repository.ChatRoomRepository;
import com.hanghaecloneproject.trade.domain.Trade;
import com.hanghaecloneproject.trade.repository.TradeReadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomJoinService {

    private final TradeReadRepository tradeReadRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinRepository chatRoomJoinRepository;

    public ChatRoomJoinService(TradeReadRepository tradeReadRepository, ChatRoomRepository chatRoomRepository,
          ChatRoomJoinRepository chatRoomJoinRepository) {
        this.tradeReadRepository = tradeReadRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomJoinRepository = chatRoomJoinRepository;
    }

    @Transactional
    public void joinChatRoom(ChatRoomCreateRequestDto dto, Long chatRoomId, Long userId) {
        Trade trade = tradeReadRepository.findById(dto.getTradeId())
              .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        Long sellerId = trade.getUserId();

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
              .orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));

        ChatRoomJoin chatRoomJoin1 = new ChatRoomJoin(userId, chatRoom);
        ChatRoomJoin chatRoomJoin2 = new ChatRoomJoin(sellerId, chatRoom);
        chatRoomJoinRepository.save(chatRoomJoin1);
        chatRoomJoinRepository.save(chatRoomJoin2);
    }
}
