package com.food.withkitchen.repository;

import com.food.withkitchen.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member getByUid(String uid);
}
