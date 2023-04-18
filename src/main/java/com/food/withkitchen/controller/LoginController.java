package com.food.withkitchen.controller;

import com.food.withkitchen.dto.LoginResultDTO;
import com.food.withkitchen.dto.MemberLoginDTO;
import com.food.withkitchen.dto.MemberSignUpDTO;
import com.food.withkitchen.service.MemberService;
import com.food.withkitchen.validators.MemberLoginDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final MemberService memberService;

    @Autowired
    LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new MemberLoginDTOValidator());
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("memberLoginDTO", new MemberLoginDTO());
        return "/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("memberLoginDTO") @Valid MemberLoginDTO memberLoginDTO, BindingResult bindingResult) {
        logger.info("[login] 로그인을 수행합니다. id : {}, password : ****", memberLoginDTO.getUsername());

        memberService.MatchPassword(memberLoginDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("[login] 유효성 검사 에러 발생 : {}", bindingResult.getAllErrors());
            return "/login";
        }

        LoginResultDTO loginResultDTO = memberService.login(memberLoginDTO);

        if (loginResultDTO.isSuccess()) {
            logger.info("[login] 로그인을 완료했습니다. token : {}", loginResultDTO.getToken());
        }

        return "redirect:/home";
    }

}
