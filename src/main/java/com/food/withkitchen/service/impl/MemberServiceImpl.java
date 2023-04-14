package com.food.withkitchen.service.impl;

import com.food.withkitchen.dto.MemberDTO;
import com.food.withkitchen.dto.MemberRequestDTO;
import com.food.withkitchen.dto.MemberResponseDTO;
import com.food.withkitchen.entity.Member;
import com.food.withkitchen.entity.Role;
import com.food.withkitchen.repository.MemberRepository;
import com.food.withkitchen.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

@RequiredArgsConstructor
@Service
@Transactional // 사용하지 않으면 메서드 수행 중 예외가 발생해도 이전에 수행한 작업은 커밋되어 DB에 반영되어 일관성이 없어진다. 사용시 예외 발생하면 롤백이 된다.
public class MemberServiceImpl implements MemberService {

    private final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    // getReferenceById는 JPA의 EntityManager에서 제공하며 LazyLoading을 사용한다. 프록시 객체를 반환한다. 프록시 객체는 실제 객체를 참조하나 DB에 접근 안한다.
    // findByid는 JPARepository에성 제공하며 EagerLoading를 사용하며 엔티티 객체를 반환한다.
//    @Override
//    public MemberResponseDTO getMember(Long id) {
//        Member findMember = memberRepository.findById(id).get();
//
//        // Member객체의 핃드 값을 MemberResponseDTO에 복사시킨다. 이때 복사하는 필드는 이름이 동일한 필드만이다. name, email등
//        // 필드명이 다르면 map().setDestField 필드 타입이 다르면 addConverter 로 매핑할 수 있다.
//
//        return modelMapper.map(findMember, MemberResponseDTO.class);
//    }
//
//    @Override
//    public MemberResponseDTO saveMember(MemberDTO memberDTO) {
//        Member member = modelMapper.map(memberDTO, Member.class);
//
//        memberRepository.save(member);
//
//        return null;
//    }
//
//    @Override
//    public MemberResponseDTO changeMemberPassword(Long id, String password) throws Exception {
//        Member findMember = memberRepository.findById(id).get();
//        findMember.setPassword(password); // jwt와 스프링시큐리티 고려해야함
//        Member saveMember = memberRepository.save(findMember);
//
//        // JPA에서는 엔티티 객체를 1차 캐시에 저장하므로 이미 조회한 Entity 객체를 수정하고저장하면 1차 캐시에 저장된 객체를 수정하고
//        // 트랜잭션 커밋 시에 변경한 내용을 DB에 반영하여 변경된 필드만 DB에 업데이트되므로 modelmapper에 의해 다른 필드들이 저장되어
//        // 성능이 큰 영향을 끼치진 않는다. 하지만 DB에 불필요한 업데이트가 발생할 수 있으므로 필요한 필드만 설정하는 것이 좋은 방법이다.
//
//
//        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
//        memberResponseDTO.setPassword(saveMember.getPassword());
//        return memberResponseDTO;
//    }

//    @Override
//    public void deleteMember(Long id) throws Exception {
//        memberRepository.deleteById(id);
//    }

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
                    .roleSet(new HashSet<>(Collections.singletonList(Role.ADMIN)))
                    .build();
        } else {
            member = Member.builder()
                    .username(memberRequestDTO.getUsername())
                    .password(passwordEncoder.encode(memberRequestDTO.getPassword()))
                    .email(memberRequestDTO.getEmail())
                    .nickname(memberRequestDTO.getNickname())
                    .roleSet(new HashSet<>(Collections.singletonList(Role.USER)))
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
}
