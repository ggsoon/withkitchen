package com.food.withkitchen.validators;

import com.food.withkitchen.dto.MemberLoginDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberLoginDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberLoginDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberLoginDTO memberLoginDTO = (MemberLoginDTO) target;

        if (StringUtils.isBlank(memberLoginDTO.getUsername()))
            errors.rejectValue("username", "NotBlank", "아이디를 입력해주세요.");

        if (StringUtils.isBlank(memberLoginDTO.getPassword()))
            errors.rejectValue("password", "NotBlank", "비밀번호를 입력해주세요.");
    }
}
