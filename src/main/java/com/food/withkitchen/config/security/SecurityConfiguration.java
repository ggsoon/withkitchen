package com.food.withkitchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) { this.jwtTokenProvider = jwtTokenProvider; }


    // WebSecurityConfigurerAdapter는 deprecated가 되었다. 이 클래스는 컴포넌트 기반의 보안 설정을 권장한다는 이유다. 대신에 SecurityFilterChain를 쓴다. 빈을 등록하고 SecurityFilterChain을 반환한다는 차이가 있다.
    // 전에는 configure 메소드를 오버라이드해 필턴 체인을 구성해 설정이 복잡해지면 코드가 복잡해진다. 반면 필터 체인 구성을 위한 빈으로 등록하면 각 보안 기능을 컴포넌트로 분리하고 필요한 컴포넌트를
    // 가져와 필터 체인을 구성해서 좀 더 모듈화해 설정이 복잡해져도 독립적으로 개발하고 유지보수가 용이해진다.
    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER) // 기본 인증 필터 체인의 순서를 지정한다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
//                .csrf().disable()

                // sessionCreationPolicy에서 STATELESS는 항상 세션을 생성하지 않고 NEVER는 세션이 존재해도 생성하지 않는 것이다.
                // jwt에선 서버 측에서 세션을 관리하지 않고 클라이언트 측에서 토큰을 전송하여 인증한다.
                .sessionManagement()
                .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                // .and()는 메소드를 연결시켜 하나의 보안 필터 체인으로 묶고 순서가 있다. 이를 사용하지 않으면 각 메소드가 별개의 작업으로 인식하여 서로 영향을 받지 않는다.
                // authorizeRequests는 HTTP 요청에 대한 인증 및 인가를 설정한다. 이 메소드를 호출하면 antmatchers를 호출하여 URL 패턴을 지정하고 패턴에 허용되는 권한을 설정한다.
                .and()
                .authorizeRequests()

                // antMatchers("/api/**").authenticated()에서 이 패턴에 대한 요청은 인증된 사용자만 허용한다는 뜻이고
                // .anyRequest().permitAll(); 는 그외의 모든 요청은 모든 사용자에게 허용한다.
                // hasRole(), hasAuthority로 권한을 가진 사용자만 요청을 처리하도록 할 수 있다.
                .antMatchers("/sign-api/**").permitAll()

                // .exceptionHandling().accessDeniedHandler()는 해당 리소스의 인가 없는(권한이 없는) 사용자에게 엑세스 접근 불가인 403이나 접근이 막혔으므로 경로 리다이렉트를 하는 핸들러를 등록한다.
                // authenticationEntryPoint()는 인증되지 않은 사용자가 리소스에 접근할 때 로그인 페이지로 리다이렉트 하도록 핸들러를 등록한다.
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                // id, pwd 필터전에 JWT 토큰을 인증한다.
                // HTTP 요청에 Authorization 헤더에 JWT 토큰이 담겨 전송되며 JwtAuthenticationFilter 필터가 이 헤더에서 토큰을 추출해 인증을 수행한다.
                // 인증이 성공하면 SecurityContext에 인증 정보를 저장하고 요청을 처리할 상태로 만든다.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v1/api-docs", "/v2/api-docs", "/v3/api-docs", "/swagger-resources/**",
                "/swagger-ui.html/**", "/webjars/**", "/swagger/**", "/sign-api/exception", "/swagger-ui/**", "/v3/**", "**");
    }
}
