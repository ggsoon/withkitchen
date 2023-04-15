package com.food.withkitchen.controller;

import com.food.withkitchen.dto.MemberRequestDTO;
import com.food.withkitchen.dto.MemberResponseDTO;
import com.food.withkitchen.repository.MemberRepository;
import com.food.withkitchen.service.MemberService;
import com.food.withkitchen.validators.MemberRequestDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(SignController.class);

    private final MemberService memberService;

    @Autowired
    SignController(MemberService memberService) {
        this.memberService = memberService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new MemberRequestDTOValidator());
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("memberRequestDTO", new MemberRequestDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid MemberRequestDTO memberRequestDTO, BindingResult bindingResult) {
        logger.info("[signup] 회원가입을 수행합니다. id : {}, password : ****, email : {}, nickname: {}, role: {}",
                memberRequestDTO.getUsername(), memberRequestDTO.getEmail(), memberRequestDTO.getNickname(), memberRequestDTO.getRole());

        memberService.DuplicateFields(memberRequestDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("[singup] 유효성 검사 에러 발생 : {}", bindingResult.getAllErrors());
            return "signup";
        }

        MemberResponseDTO memberResponseDTO = memberService.signUp(memberRequestDTO);

        logger.info("[signup] 회원가입을 완료했습니다. " + memberResponseDTO.getCode() + " " + memberResponseDTO.getMsg());

        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
