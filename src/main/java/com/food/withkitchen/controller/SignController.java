package com.food.withkitchen.controller;

import com.food.withkitchen.entity.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(SignController.class);

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/signup")
    public String signup(@RequestParam("id") String username,
                         @RequestParam("password") String password) {
        logger.info("[signup] 회원가입을 수행합니다. id: {}, password : ****", username);
        // dto 생성
        return "signup";
    }
}
