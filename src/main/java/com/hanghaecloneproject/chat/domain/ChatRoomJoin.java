package com.hanghaecloneproject.chat.domain;


import com.hanghaecloneproject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ChatRoomJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "join_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name =  "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public ChatRoomJoin(User user , ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
