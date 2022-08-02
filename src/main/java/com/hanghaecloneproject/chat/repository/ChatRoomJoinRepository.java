package com.hanghaecloneproject.chat.repository;

import com.hanghaecloneproject.chat.domain.ChatRoom;
import com.hanghaecloneproject.chat.domain.ChatRoomJoin;
import com.hanghaecloneproject.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Long>{
    List<ChatRoomJoin> findByUser(User user);
    List<ChatRoomJoin> findByChatRoom(ChatRoom chatRoom);
}
