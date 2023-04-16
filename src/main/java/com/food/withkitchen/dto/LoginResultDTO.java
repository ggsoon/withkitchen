package com.food.withkitchen.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginResultDTO extends SignUpResultDTO {

    private String token;

    @Builder
    public LoginResultDTO(int code, String msg, String token) {
       super(code, msg);
       this.token = token;
    }
}
