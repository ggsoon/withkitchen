package com.food.withkitchen.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// 회원 서비스 요청시 Member Request
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 넣을 수 있습니다.")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,16}$", message = "비밀번호는 8~16자리여야 합니다, 숫자 혹은 영문 대소문자 1개 이상 포함해야 하며 특수문자는 사용할 수 없습니다.")
    private String password;
}
