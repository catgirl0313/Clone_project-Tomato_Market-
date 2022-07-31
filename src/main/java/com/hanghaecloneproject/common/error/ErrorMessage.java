package com.hanghaecloneproject.common.error;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage implements Serializable {

    private int code;
    private String message;
    private String details;

    public ErrorMessage(ErrorCode errorCode, String details) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.details = details;
    }
}
