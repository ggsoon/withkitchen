package com.food.withkitchen.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// oncePerRequestFilter은 각 요청이 처리 시 한 번만 실행되도록 보장해 필터 체인 내 같은 필터가 중복 실행하는 걸 보장한다.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 인증 토큰이 요청 헤더에 있는 지 확인하고 있다면 토큰이 유효한지 검증한다. 토큰이 유효하면 해당 요청을 인증된 사용자의 권한으로 실행한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        logger.info("[doFilterInternal] token 값 추출 완료. token : " + token);

        logger.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("[doFilterInternal] token 값 유효성 체크 종료");
        }

        filterChain.doFilter(request, response); // 인증 토큰이 실패해도 필터 체인을 계속 진행시킬 수 있다.
    }
}
