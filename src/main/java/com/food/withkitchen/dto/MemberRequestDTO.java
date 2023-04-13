package com.food.withkitchen.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

// 회원 서비스 요청시 Member Request
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberRequestDTO {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4~10자리로 영어 소문자와 숫자만 넣을 수 있습니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z\\d]{8,16}$", message = "비밀번호는 8~16자리로 숫자 혹은 영문 대소문자 1개 이상 포함해야 하며 특수문자는 사용할 수 없습니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효하지 않은 이메일 주소입니다.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 2~10자리로 특수문자는 사용할 수 없습니다.")
    private String nickname;

    @Pattern(regexp = "^(|ADMIN)$", message = "관리자 코드를 모르실경우 아무것도 입력하지 말아 주세요.")
    private String role;
}
