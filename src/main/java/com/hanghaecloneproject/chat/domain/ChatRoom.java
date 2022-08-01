package com.hanghaecloneproject.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

}
