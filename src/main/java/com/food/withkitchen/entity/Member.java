package com.food.withkitchen.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
@Table(name = "member")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String nickname;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    // 계정이 만료되었는지 확인하는 로직
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 확인하는 로직
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정의 패스워드가 만료되었는지 확인하는 로직
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 사용가능한지 확인하는 로직
    @Override
    public boolean isEnabled() {
        return true;
    }
}
