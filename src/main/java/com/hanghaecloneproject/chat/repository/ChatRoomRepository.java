package com.hanghaecloneproject.chat.repository;

import com.hanghaecloneproject.chat.domain.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {

    List<ChatRoom> findAllByTradeId(Long tradeId);
}
