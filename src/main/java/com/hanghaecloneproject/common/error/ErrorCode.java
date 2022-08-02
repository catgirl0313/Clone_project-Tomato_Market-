package com.hanghaecloneproject.common.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 100번대는 성공과 관련된 코드입니다.
    SUCCESS(100, "성공!"),
    CREATE_SUCCESSFULLY(101, "생성 완료!"),


    // 200번대는 로그인과 관련된 코드입니다.
    NO_USER(200, "해당하는 아이디가 없습니다."),
    BAD_CREDENTIAL(201, "잘못된 로그인 정보입니다."),
    DISMISS_ACCOUNT(202, "회원탈퇴한 계정입니다."),
    INVALID_TOKEN(203, "잘못된 토큰입니다."),
    EXPIRED_TOKEN(204, "만료된 토큰입니다."),

    // 300번대는 회원가입과 관련된 코드입니다.
    DUPLICATE_USERNAME(300, "중복된 아이디입니다."),
    DUPLICATE_NICKNAME(301, "중복된 닉네임입니다."),
    BAD_PASSWORD_PATTERN(302, "적합하지 않은 비밀번호 양식입니다."),
    MISMATCH_PASSWORD(303, "비밀번호와 비밀번호 재확인이 일치하지 않습니다."),

    // 400번대는 데이터 관련된 코드입니다.
    INVALID_INPUT_VALUE(400, "잘못된 값 입력입니다."),
    METHOD_NOT_ALLOWED(401, "잘못된 값 입력입니다."),

    // 500번대는 서버와 관련된 코드입니다.

    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
