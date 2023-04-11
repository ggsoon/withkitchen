package com.food.withkitchen.controller;

import com.food.withkitchen.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index() {
        return "index";
    }

    @GetMapping("/shop")
    public String shop() {
        return "/shop";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

}
