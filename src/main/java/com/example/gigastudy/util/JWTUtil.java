package com.example.gigastudy.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

@Log4j2
public class JWTUtil {

    // 추후 환경변수로 분리
    private static final String SECRET_KEY = "Frieren=YuushaHinmeruNaraSouSitatteKotodayo^^";
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    // 토큰 생성 메서드
    public static String generateToken(Map<String, Object> claims, int minute) {
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + minute * 60 * 1000))
                .signWith(key)
                .compact();
    }

    // 토큰 검증 메서드
    public static Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            // 토큰 만료
            log.error("Token expired", e);
            throw new CustomJWTException("Token expired");
        } catch (MalformedJwtException e) {
            // 잘못된 토큰 에러
            log.error("Token is malformed", e);
            throw new CustomJWTException("Invalid token");
        } catch (JwtException e) {
            // 일반 에러
            log.error("Token error", e);
            throw new CustomJWTException("Token error");
        } catch (Exception e) {
            // 그 외 에러
            log.error("Unexpected error", e);
            throw new CustomJWTException("Error processing token");
        }
    }
}
