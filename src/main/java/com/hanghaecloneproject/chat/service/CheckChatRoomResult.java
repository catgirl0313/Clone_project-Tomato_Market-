package com.hanghaecloneproject.chat.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckChatRoomResult {

    private boolean isExist;
    private Long chatRoomId;
}
