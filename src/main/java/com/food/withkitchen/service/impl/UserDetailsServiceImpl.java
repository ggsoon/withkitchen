package com.food.withkitchen.service.impl;

import com.food.withkitchen.entity.Member;
import com.food.withkitchen.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final MemberRepository memberRepository;

    // 조회된 정보를 매핑해 반환하는 UserDetails 객체는 인증 정보를 담고 있으며 인증이 필요한 리소스에 접근 시 이 객체로 권한 확인이 이루어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("[loadUserByUsername] loadUserByUsername 수행. username: {}", username);
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            logger.info("[loadUserByUsername] loadUserByUsername 성공. username: {}", username);
            return member.get();
        }

        throw new UsernameNotFoundException("존재하지 않거나 유효하지 않은 사용자입니다.");
    }

}