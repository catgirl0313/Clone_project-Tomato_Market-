package com.hanghaecloneproject.user.controller;

import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.common.error.ErrorMessage;
import com.hanghaecloneproject.user.exception.BadPasswordPatternException;
import com.hanghaecloneproject.user.exception.DuplicateNicknameException;
import com.hanghaecloneproject.user.exception.DuplicateUsernameException;
import com.hanghaecloneproject.user.exception.MismatchedPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.hanghaecloneproject.user.controller")
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> badPasswordPatternException(BadPasswordPatternException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new ErrorMessage(ErrorCode.BAD_PASSWORD_PATTERN, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> duplicateNicknameException(DuplicateNicknameException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new ErrorMessage(ErrorCode.DUPLICATE_NICKNAME, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> duplicateUsernameException(DuplicateUsernameException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new ErrorMessage(ErrorCode.DUPLICATE_USERNAME, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> mismatchedPasswordException(MismatchedPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new ErrorMessage(ErrorCode.MISMATCH_PASSWORD, e.getMessage()));
    }
}
