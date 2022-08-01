package com.hanghaecloneproject.common.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "공통 처리 Response Message")
public class CommonResponse<T> implements Serializable {


    @ApiModelProperty(value = "자체 에러 코드") private int code;
    @ApiModelProperty(value = "메세지") private String message;
    @ApiModelProperty(value = "상세") private String details;
    @ApiModelProperty(value = "결과 데이터") private T data;

    public CommonResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CommonResponse(ErrorCode errorCode, String details) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.details = details;
    }

    public CommonResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }


}
