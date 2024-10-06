package com.example.gigastudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.gigastudy.security.handler.LoginFailHandler;
import com.example.gigastudy.security.handler.LoginSuccessHandler;
import com.example.gigastudy.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

// 개발 시에 적용하는 시큐리티 설정
// application.properties에서 편집한다
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("dev")
public class SecurityConfigDev {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf->csrf.disable()) // 개발 시 CSRF 비활성화
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/fonts/**", "/api/user").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailHandler())
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션방식의 로그인은 사용하지않음
            )
            .logout((logout) -> logout.permitAll())
            .userDetailsService(userDetailsServiceImpl);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 이렇게 PasswordEncoder와 UserDetailsService가 구현되어있으면
    // 스프링 시큐리티는 자동으로 DaoAuthenticationProvider를 설정하여 사용자 인증을 처리한다
}
