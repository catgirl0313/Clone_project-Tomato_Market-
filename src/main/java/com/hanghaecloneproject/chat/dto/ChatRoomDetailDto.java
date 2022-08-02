package com.hanghaecloneproject.chat.dto;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDetailDto {
    private Long senderId;
    private String senderName;
    private String senderEmail;
    private String receiverName;
    private List<ChatMessage> messages;
    private Long chatRoomId;
}
