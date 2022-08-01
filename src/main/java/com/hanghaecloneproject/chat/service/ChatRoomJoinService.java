package com.hanghaecloneproject.chat.service;

import com.hanghaecloneproject.chat.domain.ChatRoom;
import com.hanghaecloneproject.chat.domain.ChatRoomJoin;
import com.hanghaecloneproject.chat.repository.ChatRoomJoinRepository;
import com.hanghaecloneproject.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatRoomJoinService {

    private final ChatRoomJoinRepository chatRoomJoinRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Transactional
    public Long newRoom(String user1, String user2) {

        ChatRoom chatRoom = new ChatRoom();
        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);
            //두명 다 입장 ??????????
            createRoom(user1,newChatRoom);
            createRoom(user2,newChatRoom);

        return newChatRoom.getId();
    }

    @Transactional
    public void createRoom(String user, ChatRoom chatRoom){
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin(userService.findUserByEmailMethod(user),chatRoom);
        chatRoomJoinRepository.save(chatRoomJoin);
    }
}
