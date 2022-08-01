package com.hanghaecloneproject.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    public ChatMessage(String message, LocalDateTime time, ChatRoom chatRoom, User writer) {
        this.message = message;
        this.time = time;
        this.chatRoom = chatRoom;
        this.writer = writer;
    }
}
