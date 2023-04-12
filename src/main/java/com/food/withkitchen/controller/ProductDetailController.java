package com.food.withkitchen.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class ProductDetailController {
}

//  @PreAuthorize("hasRole('USER')")
//  @PostAuthorize("returnObject.author.username == authentication.name") // 동적인 보안으로 자신의 게시물만 조회하는 경우, SpeL 표현식(객체 그래프 조회와 조작)
//  상품상세 페이지 @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

