package com.hanghaecloneproject.user.controller;

import com.hanghaecloneproject.user.error.ErrorCode;
import com.hanghaecloneproject.user.exception.BadPasswordPatternException;
import com.hanghaecloneproject.user.exception.DuplicateNicknameException;
import com.hanghaecloneproject.user.exception.DuplicateUsernameException;
import com.hanghaecloneproject.user.exception.MismatchedPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "${com.hanghaecloneproject.user.controller.*}")
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorCode> badPasswordPatternException(BadPasswordPatternException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ErrorCode.BAD_PASSWORD_PATTERN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorCode> duplicateNicknameException(DuplicateNicknameException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ErrorCode.DUPLICATE_NICKNAME);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorCode> duplicateUsernameException(DuplicateUsernameException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ErrorCode.DUPLICATE_USERNAME);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorCode> mismatchedPasswordException(MismatchedPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ErrorCode.MISMATCH_PASSWORD);
    }

}
