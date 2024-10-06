package com.example.gigastudy.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.gigastudy.entity.User;
import com.example.gigastudy.util.JWTUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// 로그인에 성공하면 수행하는 클래스
@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        log.info("로그인에 성공하였습니다." + authentication);

        User user = (User) authentication.getPrincipal();

        // 토큰을 만들기위한 claim을 만들어준다
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("username", user.getUsername());
        claims.put("nickname", user.getNickname());

        // 로그인 성공시 두개의 토큰을 만들어준다, 하나는 액세스토큰, 하나는 리프레시토큰
        String accessToken = JWTUtil.generateToken(claims, 2 * 60);
        String refreshToken = JWTUtil.generateToken(claims, 24 * 60);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);

        // 결과를 json파일로 전송
        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

    }

}
