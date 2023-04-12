package com.food.withkitchen.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/mypage")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String myPage() {
        return "mypage";
    }

    @GetMapping("/adminpage")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminPage() {
        return "adminpage";
    }
}
