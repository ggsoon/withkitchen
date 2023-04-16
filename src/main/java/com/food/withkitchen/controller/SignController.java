package com.food.withkitchen.controller;

import com.food.withkitchen.dto.LoginResultDTO;
import com.food.withkitchen.dto.MemberLoginDTO;
import com.food.withkitchen.dto.MemberSignUpDTO;
import com.food.withkitchen.dto.SignUpResultDTO;
import com.food.withkitchen.service.MemberService;
import com.food.withkitchen.validators.MemberLoginDTOValidator;
import com.food.withkitchen.validators.MemberSignUpDTOValidator;
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

    @InitBinder("memberSignUpDTO")
    public void initSignUpBinder(WebDataBinder binder) {
        binder.setValidator(new MemberSignUpDTOValidator());
    }

    @InitBinder("memberLoginDTO")
    public void initLoginBinder(WebDataBinder binder) {
        binder.setValidator(new MemberLoginDTOValidator());
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("memberSignUpDTO", new MemberSignUpDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("memberSignUpDTO") @Valid MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult) {
        logger.info("[signup] 회원가입을 수행합니다. id : {}, password : ****, email : {}, nickname: {}, role: {}",
                memberSignUpDTO.getUsername(), memberSignUpDTO.getEmail(), memberSignUpDTO.getNickname(), memberSignUpDTO.getRole());

        memberService.DuplicateFields(memberSignUpDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("[singup] 유효성 검사 에러 발생 : {}", bindingResult.getAllErrors());
            return "signup";
        }

        SignUpResultDTO signUpResultDTO = memberService.signUp(memberSignUpDTO);

        logger.info("[signup] 회원가입을 완료했습니다. " + signUpResultDTO.getCode() + " " + signUpResultDTO.getMsg());

        return "redirect:login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("memberLoginDTO", new MemberLoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("memberLoginDTO") @Valid MemberLoginDTO memberLoginDTO, BindingResult bindingResult) {
        logger.info("[login] 로그인을 수행합니다. id : {}, password : ****", memberLoginDTO.getUsername(), memberLoginDTO.getPassword());

        if (bindingResult.hasErrors()) {
            logger.error("[login] 유효성 검사 에러 발생 : {}", bindingResult.getAllErrors());
            return "login";
        }

        LoginResultDTO loginResultDTO = memberService.login(memberLoginDTO);

        if (loginResultDTO.getCode() == 1) {
            logger.info("[login] 로그인을 완료했습니다. id : {}, token : {}", loginResultDTO.getCode(), loginResultDTO.getToken());
        }

        return "redirect:home";
    }
}
