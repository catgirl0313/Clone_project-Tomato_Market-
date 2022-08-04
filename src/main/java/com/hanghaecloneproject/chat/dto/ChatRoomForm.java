package com.hanghaecloneproject.chat.dto;

import com.hanghaecloneproject.chat.domain.ChatMessage;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomForm {

    private Long id;
    private Long tradeId;
    private String lastMessage;
    private LocalDateTime time;

    public ChatRoomForm(Long tradeId, ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.tradeId = tradeId;
        this.lastMessage = chatMessage.getMessage();
        this.time = chatMessage.getCreatedAt();
    }


}
