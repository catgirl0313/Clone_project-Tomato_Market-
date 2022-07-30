package com.hanghaecloneproject.user.exception;

public class MismatchedPasswordException extends RuntimeException {

    public MismatchedPasswordException(String message) {
        super(message);
    }
}
