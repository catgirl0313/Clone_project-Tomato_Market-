package com.hanghaecloneproject.user.controller;

import com.hanghaecloneproject.user.dto.CheckNicknameDto;
import com.hanghaecloneproject.user.dto.CheckUsernameDto;
import com.hanghaecloneproject.user.dto.SignupRequestDto;
import com.hanghaecloneproject.user.service.UserService;
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

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(SignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
              .body(null);
    }

    @GetMapping("/idCheck/{username}")
    public ResponseEntity<Void> idCheck(@PathVariable String username) {
        userService.checkDuplicateUsername(new CheckUsernameDto(username));
        return ResponseEntity.status(HttpStatus.OK)
              .body(null);
    }

    @PostMapping("/nicknameCheck/{nickname}")
    public ResponseEntity<Void> nameCheck(@PathVariable String nickname) {
        userService.checkDuplicateNickname(new CheckNicknameDto(nickname));
        return ResponseEntity.status(HttpStatus.OK)
              .body(null);
    }
}
