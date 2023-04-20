package com.food.withkitchen.validators;

import com.food.withkitchen.dto.UserLoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserLoginDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserLoginDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) target;

        if (StringUtils.isBlank(userLoginDTO.getUsername()))
            errors.rejectValue("username", "NotBlank", "아이디를 입력해주세요.");

        if (StringUtils.isBlank(userLoginDTO.getPassword()))
            errors.rejectValue("password", "NotBlank", "비밀번호를 입력해주세요.");
    }
}
