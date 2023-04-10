package com.food.withkitchen.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "member")
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = true, length = 255)
    private String uid; // JWT 토큰 내 정보

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column(nullable = false, unique = true, length = 15)
    private String username;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.uid;
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
