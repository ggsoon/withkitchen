package com.food.withkitchen.validators;

import com.food.withkitchen.dto.MemberRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberRequestDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberRequestDTO memberRequestDTO = (MemberRequestDTO) target;

        if (StringUtils.isBlank(memberRequestDTO.getUsername())) {
            errors.rejectValue("username", "NotBlank", "아이디를 입력해주세요.");
        } else if (!memberRequestDTO.getUsername().matches("^[a-z0-9]{4,10}$")) {
            errors.rejectValue("username", "Pattern", "아이디는 4~10자리로 영어 소문자와 숫자만 넣을 수 있습니다.");
        }

        if (StringUtils.isBlank(memberRequestDTO.getPassword())) {
            errors.rejectValue("password", "NotBlank", "비밀번호를 입력해주세요.");
        } else if (!memberRequestDTO.getPassword().matches("^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,16}$")) {
            errors.rejectValue("password", "Pattern", "비밀번호는 8~16자리로 숫자 혹은 영문 대소문자 1개 이상 포함해야 하며 특수문자는 사용할 수 없습니다.");
        }

        if (StringUtils.isBlank(memberRequestDTO.getEmail())) {
            errors.rejectValue("email", "NotBlank", "이메일을 입력해주세요.");
        } else if (!memberRequestDTO.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            errors.rejectValue("email", "Email", "유효하지 않은 이메일 주소입니다.");
        }

        if (StringUtils.isBlank(memberRequestDTO.getNickname())) {
            errors.rejectValue("nickname", "NotBlank", "닉네임을 입력해주세요.");
        } else if (!memberRequestDTO.getNickname().matches("^[가-힣a-zA-Z0-9]{2,10}$")) {
            errors.rejectValue("nickname", "Pattern", "닉네임은 2~10자리로 특수문자는 사용할 수 없습니다.");
        }

        if (StringUtils.isNotBlank(memberRequestDTO.getRole()) && !memberRequestDTO.getRole().matches("^(|ADMIN)$"))
            errors.rejectValue("role", "Pattern", "관리자 코드를 모르실경우 아무것도 입력하지 말아 주세요.");
    }
}
