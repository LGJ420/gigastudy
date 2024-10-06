package com.example.gigastudy.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// 로그인에 실패하면 수행하는 클래스
@Log4j2
public class LoginFailHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        
        log.info("로그인에 실패하였습니다." + exception);

        // 결과를 json파일로 전송
        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of("error", "ERROR_LOGIN"));

        response.setContentType("application/json");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
