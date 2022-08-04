package com.hanghaecloneproject.chat.repository;

import com.hanghaecloneproject.chat.domain.ChatRoomJoin;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Long>{
    List<ChatRoomJoin> findByUserId(Long userId);

    List<ChatRoomJoin> findByChatRoomId(Long chatRoomId);

    Optional<ChatRoomJoin> findByUserIdAndChatRoomId(Long userId, Long chatRoomId);
}
