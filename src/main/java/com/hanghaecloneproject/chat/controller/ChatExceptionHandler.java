package com.hanghaecloneproject.chat.controller;

import com.hanghaecloneproject.common.error.CommonResponse;
import com.hanghaecloneproject.common.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(basePackages = "com.hanghaecloneproject.chat.controller")
public class ChatExceptionHandler {

    @MessageExceptionHandler
    public ResponseEntity<CommonResponse<String>> failToCreateRoom(
          IllegalStateException illegalStateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new CommonResponse<>(ErrorCode.INVALID_INPUT_VALUE));
    }
}
