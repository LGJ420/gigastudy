package com.example.gigastudy.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.gigastudy.entity.User;
import com.example.gigastudy.entity.UserRole;
import com.example.gigastudy.util.JWTUtil;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    // shouldNotFilter는 경로에 로그인 체크여부를 설정할 수 있다
    // @Override
    // protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    //     String path = request.getRequestURI();

    //     // 예시) /api/user/ 경로의 호출은 로그인 체크하지 않음
    //     if (path.startsWith("/api/user/")) {
    //         return true;
    //     }

    //     return false;
    // }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");

        if (authHeaderStr == null || !authHeaderStr.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Authorization 헤더가 없으면 필터를 넘어간다.
            return;
        }

        try {
            // Bearer 뒤에 붙은 토큰을 추출한다.
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            // 클레임에서 유저 정보 추출
            String username = (String) claims.get("username");
            String nickname = (String) claims.get("nickname");
            String role = (String) claims.get("role");
            Object idObject = claims.get("id");

            // id 값은 (Long)으로 캐스팅하려고 해도 예외가 발생해서
            // Integer로 저장될 수 있기 때문에 Long으로 강제변환
            Long userId;
            if (idObject instanceof Integer) {
                userId = ((Integer) idObject).longValue();
            } else {
                userId = (Long) idObject;
            }

            // User 객체 생성 (실제 UserDetails 구현체)
            User user = User.builder()
                    .id(userId)
                    .username(username)
                    .nickname(nickname)
                    .role(UserRole.valueOf(role))
                    .build();

            // 인증 객체 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            // SecurityContextHolder에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 다음 필터로 넘김
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Token validation error: ", e);
            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
