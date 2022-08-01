package com.hanghaecloneproject.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDto {
    private Long senderId;
    private String senderName;
    private String senderEmail;
    private List<ChatRoomForm> chatRooms;
}
