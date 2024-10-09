package com.example.gigastudy.entity;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // username 명칭 대체
    @Override
    public String getUsername() {
        return userId;  // userId를 username으로 사용
    }

    // password 명칭 대체
    @Override
    public String getPassword() {
        return userPw;  // userPw를 password로 사용
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한을 반환
        return Collections.singleton(() -> "ROLE_" + role.name());
    }
    
    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았음을 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠기지 않았음을 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 자격 증명이 만료되지 않았음을 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되어 있음을 반환
        return true;
    }
}
