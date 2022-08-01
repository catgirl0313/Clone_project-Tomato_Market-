package com.hanghaecloneproject;

import com.hanghaecloneproject.config.security.dto.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestingController {

    @GetMapping("/")
    public String sayHello(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(userDetails.toString());
        return "CI/CD 테스트가 잘 나오네요!";
    }
}
