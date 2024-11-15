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
import com.example.gigastudy.security.handler.CustomAccessDeniedHandler;
import com.example.gigastudy.security.handler.LoginFailHandler;
import com.example.gigastudy.security.handler.LoginSuccessHandler;
import com.example.gigastudy.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

// 개발 시에 적용하는 시큐리티 설정
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("dev")
public class SecurityConfigDev {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 토큰을 사용하므로 CSRF 보호는 필요없어진다.
            .csrf(csrf->csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("test", "test1", "test2", "/word/**", "/signup", "/complete", "/", "/favicon.ico", "/error", "/css/**", "/js/**", "/images/**", "/fonts/**", "/api/user").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
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
            .exceptionHandling(config -> {
                config.authenticationEntryPoint((request, response, authException) -> {
                    // 일반적으로는 login 페이지로 리다이렉트 한다고 함
                    // 여기서는 에러 페이지로 리다이렉트
                    response.sendRedirect("/error");
                });
                config.accessDeniedHandler(new CustomAccessDeniedHandler());
            })
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