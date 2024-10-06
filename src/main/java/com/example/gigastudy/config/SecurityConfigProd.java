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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.gigastudy.security.filter.JWTCheckFilter;
import com.example.gigastudy.security.handler.CustomAccessDenieHandler;
import com.example.gigastudy.security.handler.LoginFailHandler;
import com.example.gigastudy.security.handler.LoginSuccessHandler;
import com.example.gigastudy.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

// 배포 시에 적용하는 시큐리티 설정
// application.properties에서 편집한다
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("prod")
public class SecurityConfigProd {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailHandler())
                .permitAll()
            )
            // 세션방식의 로그인 사용중지
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // JWT를 검증할 필터 추가
            .addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class)
            // 사용자가 접근권한이 없을경우 처리
            .exceptionHandling(config->{
                config.accessDeniedHandler(new CustomAccessDenieHandler());
            })
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
