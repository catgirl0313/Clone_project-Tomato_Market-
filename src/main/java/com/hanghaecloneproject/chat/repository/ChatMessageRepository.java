package com.hanghaecloneproject.chat.repository;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
