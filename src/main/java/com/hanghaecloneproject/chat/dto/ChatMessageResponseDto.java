package com.hanghaecloneproject.chat.dto;


import com.hanghaecloneproject.chat.domain.ChatMessage;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponseDto {

    private Long chatRoomId;
    private String message;
    private String profile;
    private String nickname;
    private LocalDateTime createdAt;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.chatRoomId = chatMessage.getChatRoom().getId();
        this.message = HtmlUtils.htmlEscape(chatMessage.getMessage());
        this.profile = chatMessage.getWriter().getProfileImage();
        this.nickname = chatMessage.getWriter().getNickname();
        this.createdAt = chatMessage.getCreatedAt();
    }
}
