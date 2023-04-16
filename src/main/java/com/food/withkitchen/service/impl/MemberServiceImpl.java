package com.food.withkitchen.service.impl;

import com.food.withkitchen.config.security.JwtTokenProvider;
import com.food.withkitchen.dto.LoginResultDTO;
import com.food.withkitchen.dto.MemberLoginDTO;
import com.food.withkitchen.dto.MemberSignUpDTO;
import com.food.withkitchen.dto.SignUpResultDTO;
import com.food.withkitchen.entity.Member;
import com.food.withkitchen.entity.Role;
import com.food.withkitchen.repository.MemberRepository;
import com.food.withkitchen.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public SignUpResultDTO signUp(MemberSignUpDTO memberSignUpDTO) {
        logger.info("[signUp] 회원 가입 정보 전달됨");
        Member member;

        if (memberSignUpDTO.getRole().equalsIgnoreCase("ADMIN")) {
            member = Member.builder()
                    .username(memberSignUpDTO.getUsername())
                    .password(passwordEncoder.encode(memberSignUpDTO.getPassword()))
                    .email(memberSignUpDTO.getEmail())
                    .nickname(memberSignUpDTO.getNickname())
                    .role(Role.ROLE_ADMIN)
                    .build();
        } else {
            member = Member.builder()
                    .username(memberSignUpDTO.getUsername())
                    .password(passwordEncoder.encode(memberSignUpDTO.getPassword()))
                    .email(memberSignUpDTO.getEmail())
                    .nickname(memberSignUpDTO.getNickname())
                    .role(Role.ROLE_USER)
                    .build();
        }

        Member savedMember = memberRepository.save(member);
        SignUpResultDTO signUpResultDTO = new SignUpResultDTO();

        logger.info("[signUp] Member Entity 값이 정상적으로 들어왔는지 확인");
        if (!savedMember.getUsername().isEmpty()) {
            signUpResultDTO.setCode(1);
            signUpResultDTO.setMsg("Success");
            logger.info("[signUp] 정상 처리 완료");
        } else {
            signUpResultDTO.setCode(0);
            signUpResultDTO.setMsg("Error");
            logger.info("[signUp] 실패 처리 완료");
        }

        return signUpResultDTO;
    }

    @Override
    public void DuplicateFields(MemberSignUpDTO memberSignUpDTO, BindingResult bindingResult) {
        if (memberRepository.existsByUsername(memberSignUpDTO.getUsername()))
            bindingResult.rejectValue("username", "Duplicate", "이미 사용 중인 아이디입니다.");
        if (memberRepository.existsByNickname(memberSignUpDTO.getNickname()))
            bindingResult.rejectValue("nickname", "Duplicate", "이미 사용 중인 닉네임입니다.");
        if (memberRepository.existsByEmail(memberSignUpDTO.getEmail()))
            bindingResult.rejectValue("email", "Duplicate", "이미 사용 중인 이메일입니다.");
    }

    @Override
    public LoginResultDTO login(MemberLoginDTO memberLoginDTO) throws RuntimeException {
        logger.info("[login] 로그인 정보 전달됨");
        Member member = memberRepository.findByUsername(memberLoginDTO.getUsername()).orElseThrow();
        logger.info("[login] Id: {}", memberLoginDTO.getUsername());

        logger.info("[login] 패스워드 비교 수행)");
        if (!passwordEncoder.matches(memberLoginDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException();
        }
        logger.info("[login] 패스워드 일치)");

        logger.info("[login] signUpResultDTO 객체 생성)");
        LoginResultDTO loginResultDTO = LoginResultDTO.builder()
                        .token(jwtTokenProvider.createToken(String.valueOf(member.getUsername()),
                                member.getRole()))
                        .code(1)
                        .msg("Success")
                        .build();
        logger.info("[login] signUpResultDTO 객체에 값 주입)");

        return loginResultDTO;
    }
}
