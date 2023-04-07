package com.food.withkitchen.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v1/api-docs", "/v2/api-docs", "/v3/api-docs", "/swagger-resources/**",
                "/swagger-ui.html/**", "/webjars/**", "/swagger/**", "/sign-api/exception", "/swagger-ui/**", "/v3/**", "**");
    }
}
