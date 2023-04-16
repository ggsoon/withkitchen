package com.food.withkitchen.service;

import com.food.withkitchen.dto.LoginResultDTO;
import com.food.withkitchen.dto.MemberLoginDTO;
import com.food.withkitchen.dto.MemberSignUpDTO;
import com.food.withkitchen.dto.SignUpResultDTO;
import org.springframework.validation.BindingResult;

public interface MemberService {
    
    SignUpResultDTO signUp(MemberSignUpDTO memberSignUpDTO);

    void DuplicateFields(MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult);

    LoginResultDTO login(MemberLoginDTO memberLoginDTO);
}
