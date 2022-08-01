package com.hanghaecloneproject.chat.repository;

import com.hanghaecloneproject.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long> {
    Optional<ChatRoom> findById(Long id);

}
