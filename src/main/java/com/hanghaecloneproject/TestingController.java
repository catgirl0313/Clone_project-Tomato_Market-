package com.hanghaecloneproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

    @GetMapping("/")
    public String sayHello() {
        return "CI/CD 테스트가 잘 나오네요!";
    }
}
