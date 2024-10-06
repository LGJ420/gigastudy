package com.example.gigastudy.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;

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
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
