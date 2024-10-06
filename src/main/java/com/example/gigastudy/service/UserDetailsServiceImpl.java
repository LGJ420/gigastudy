package com.example.gigastudy.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gigastudy.entity.User;
import com.example.gigastudy.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// 스프링 시큐리티는 UserDetailService가 필요함
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        // User 엔티티를 반환 (User는 UserDetails를 구현하고 있으므로 바로 반환 가능)
        return user;
    }
}