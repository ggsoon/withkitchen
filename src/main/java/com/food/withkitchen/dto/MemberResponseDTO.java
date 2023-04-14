package com.food.withkitchen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.BindingResult;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberResponseDTO {

    private int code;

    private String msg;

}
