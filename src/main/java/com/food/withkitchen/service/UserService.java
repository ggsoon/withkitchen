package com.food.withkitchen.service;

import com.food.withkitchen.dto.UserLoginDTO;
import com.food.withkitchen.dto.UserSignUpDTO;
import org.springframework.validation.BindingResult;

public interface UserService {
    
    void signUp(UserSignUpDTO userSignUpDTO);

    void DuplicateFields(UserSignUpDTO userSignUpDTO, BindingResult bindingResult);

    void login(UserLoginDTO userLoginDTO);

    void MatchPassword(UserLoginDTO userLoginDTO, BindingResult bindingResult);
}
