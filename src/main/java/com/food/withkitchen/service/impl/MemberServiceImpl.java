package com.food.withkitchen.service.impl;

import com.food.withkitchen.dto.MemberDTO;
import com.food.withkitchen.dto.MemberResponseDTO;
import com.food.withkitchen.entity.Member;
import com.food.withkitchen.repository.MemberRepository;
import com.food.withkitchen.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional // 사용하지 않으면 메서드 수행 중 예외가 발생해도 이전에 수행한 작업은 커밋되어 DB에 반영되어 일관성이 없어진다. 사용시 예외 발생하면 롤백이 된다.
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;

    // getReferenceById는 JPA의 EntityManager에서 제공하며 LazyLoading을 사용한다. 프록시 객체를 반환한다. 프록시 객체는 실제 객체를 참조하나 DB에 접근 안한다.
    // findByid는 JPARepository에성 제공하며 EagerLoading를 사용하며 엔티티 객체를 반환한다.
    @Override
    public MemberResponseDTO getMember(Long id) {
        Member findMember = memberRepository.findById(id).get();

        // Member객체의 핃드 값을 MemberResponseDTO에 복사시킨다. 이때 복사하는 필드는 이름이 동일한 필드만이다. name, email등
        // 필드명이 다르면 map().setDestField 필드 타입이 다르면 addConverter 로 매핑할 수 있다.

        return modelMapper.map(findMember, MemberResponseDTO.class);
    }

    @Override
    public MemberResponseDTO saveMember(MemberDTO memberDTO) {
        return null;
    }

    @Override
    public MemberResponseDTO changeMemberPassword(Long id, String password) throws Exception {
        return null;
    }

    @Override
    public void deleteMember(Long id) throws Exception {

    }
}
