package com.food.withkitchen.service;

import com.food.withkitchen.dto.MemberDTO;
import com.food.withkitchen.dto.MemberResponseDTO;

public interface MemberService {

    MemberResponseDTO getMember(Long id);

    MemberResponseDTO saveMember(MemberDTO memberDTO);

    MemberResponseDTO changeMemberPassword(Long id, String password) throws Exception;

    void deleteMember(Long id) throws Exception;

}
