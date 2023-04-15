package com.food.withkitchen.service;

import com.food.withkitchen.dto.MemberRequestDTO;
import com.food.withkitchen.dto.MemberResponseDTO;
import org.springframework.validation.BindingResult;

public interface MemberService {
    
    MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO);

    void DuplicateFields(MemberRequestDTO memberRequestDTO, BindingResult bindingResult);
}
