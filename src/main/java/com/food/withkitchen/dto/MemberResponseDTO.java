package com.food.withkitchen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// API 응답 결과를 나타내어 필요한 정보만 담아 응답 결과를 표현한다, API 응답 결과가 변경되어도 비즈니스 로직 처리와 관련이 없어 영향을 최소화
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberResponseDTO {

    private Long id;

    private String username;

    private String password;

    private String email;
}
