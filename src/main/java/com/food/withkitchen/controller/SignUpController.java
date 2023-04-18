package com.food.withkitchen.controller;

import com.food.withkitchen.dto.MemberSignUpDTO;
import com.food.withkitchen.dto.SignUpResultDTO;
import com.food.withkitchen.service.MemberService;
import com.food.withkitchen.validators.MemberSignUpDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class SignUpController {

    private final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    private final MemberService memberService;

    @Autowired
    SignUpController(MemberService memberService) {
        this.memberService = memberService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new MemberSignUpDTOValidator());
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("memberSignUpDTO", new MemberSignUpDTO());
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("memberSignUpDTO") @Valid MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult) {
        logger.info("[signup] 회원가입을 수행합니다. id : {}, password : ****, email : {}, nickname: {}, role: {}",
                memberSignUpDTO.getUsername(), memberSignUpDTO.getEmail(), memberSignUpDTO.getNickname(), memberSignUpDTO.getRole());

        memberService.DuplicateFields(memberSignUpDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("[singup] 유효성 검사 에러 발생 : {}", bindingResult.getAllErrors());
            return "/signup";
        }

        SignUpResultDTO signUpResultDTO = memberService.signUp(memberSignUpDTO);

        logger.info("[signup] 회원가입을 완료했습니다. " + signUpResultDTO.getMsg());

        return "redirect:/about";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("[logout] 로그아웃을 수행합니다.");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);

        logger.info("[logout] 로그아웃을 성공했습니다.");
        return "redirect:/login?logout";
    }

}
