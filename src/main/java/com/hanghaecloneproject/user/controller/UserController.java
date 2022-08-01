package com.hanghaecloneproject.user.controller;

import com.hanghaecloneproject.common.error.CommonResponse;
import com.hanghaecloneproject.common.error.ErrorCode;
import com.hanghaecloneproject.user.dto.CheckNicknameDto;
import com.hanghaecloneproject.user.dto.CheckUsernameDto;
import com.hanghaecloneproject.user.dto.SignupRequestDto;
import com.hanghaecloneproject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입", notes = "회원 목록을 넣어 <big>회원가입</big>을 사용합니다.", response = ResponseEntity.class)
    @PostMapping("/signup")

    public ResponseEntity<CommonResponse<Void>> signup(SignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
              .body(new CommonResponse<>(ErrorCode.CREATE_SUCCESSFULLY));
    }

    @ApiOperation(value = "아이디 중복 검사", notes = "<big>아이디 중복검사</big>를 지원하는 API 입니다.", response = ResponseEntity.class)
    @GetMapping("/idCheck/{username}")
    public ResponseEntity<CommonResponse<Void>> idCheck(@PathVariable String username) {
        userService.checkDuplicateUsername(new CheckUsernameDto(username));
        return ResponseEntity.status(HttpStatus.OK)
              .body(new CommonResponse<>(ErrorCode.SUCCESS));
    }

    @ApiOperation(value = "닉네임 중복검사", notes = "<big>닉네임 중복검사</big>를 지원하는 API 입니다.", response = ResponseEntity.class)
    @GetMapping("/nicknameCheck/{nickname}")
    public ResponseEntity<CommonResponse<Void>> nameCheck(@PathVariable String nickname) {
        userService.checkDuplicateNickname(new CheckNicknameDto(nickname));
        return ResponseEntity.status(HttpStatus.OK)
              .body(new CommonResponse<>(ErrorCode.SUCCESS));
    }
}
