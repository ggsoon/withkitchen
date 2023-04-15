package com.food.withkitchen.service.impl;

import com.food.withkitchen.dto.MemberRequestDTO;
import com.food.withkitchen.dto.MemberResponseDTO;
import com.food.withkitchen.entity.Member;
import com.food.withkitchen.entity.Role;
import com.food.withkitchen.repository.MemberRepository;
import com.food.withkitchen.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberResponseDTO signUp(MemberRequestDTO memberRequestDTO) {
        logger.info("[signUp] 회원 가입 정보 전달됨");
        Member member;

        if (memberRequestDTO.getRole().equalsIgnoreCase("ADMIN")) {
            member = Member.builder()
                    .username(memberRequestDTO.getUsername())
                    .password(passwordEncoder.encode(memberRequestDTO.getPassword()))
                    .email(memberRequestDTO.getEmail())
                    .nickname(memberRequestDTO.getNickname())
                    .role(Role.ADMIN)
                    .build();
        } else {
            member = Member.builder()
                    .username(memberRequestDTO.getUsername())
                    .password(passwordEncoder.encode(memberRequestDTO.getPassword()))
                    .email(memberRequestDTO.getEmail())
                    .nickname(memberRequestDTO.getNickname())
                    .role(Role.USER)
                    .build();
        }

        Member savedMember = memberRepository.save(member);
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();

        logger.info("[signUp] Member Entity 값이 정상적으로 들어왔는지 확인");
        if (!savedMember.getUsername().isEmpty()) {
            memberResponseDTO.setCode(1);
            memberResponseDTO.setMsg("Success");
            logger.info("[signUp] 정상 처리 완료");
        } else {
            memberResponseDTO.setCode(0);
            memberResponseDTO.setMsg("Error");
            logger.info("[signUp] 실패 처리 완료");
        }

        return memberResponseDTO;
    }

    @Override
    public void DuplicateFields(MemberRequestDTO memberRequestDTO, BindingResult bindingResult) {
        if (memberRepository.existsByUsername(memberRequestDTO.getUsername()))
            bindingResult.rejectValue("username", "Duplicate", "이미 사용 중인 아이디입니다.");
        if (memberRepository.existsByNickname(memberRequestDTO.getNickname()))
            bindingResult.rejectValue("nickname", "Duplicate", "이미 사용 중인 닉네임입니다.");
        if (memberRepository.existsByEmail(memberRequestDTO.getEmail()))
            bindingResult.rejectValue("email", "Duplicate", "이미 사용 중인 이메일입니다.");
    }
}
