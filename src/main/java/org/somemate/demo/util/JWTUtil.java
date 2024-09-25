package org.somemate.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    private final String salt = "somemate-secret-key-20240905-somemate-secret-key-20240905-somemate-secret-key-20240905";
    private final long accessTokenExpireTime = 3600000; // 1시간
    private final long refreshTokenExpireTime = Duration.ofDays(30).toMillis();
    private final Key secretKey;

    // 생성자에서 서명 키를 초기화
    public JWTUtil() {
        this.secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // AccessToken 생성
    public String generateToken(String userId) {
        return create(userId, "accessToken", accessTokenExpireTime);
    }

    // RefreshToken 생성
    public String createRefreshToken(String userId) {
        return create(userId, "refreshToken", refreshTokenExpireTime);
    }

    // JWT에서 userId 추출
    public String extractUserId(String token) {
//        system.out
        return extractAllClaims(token).getSubject();
    }

    // JWT 토큰에서 모든 클레임을 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰 생성 메서드
    public String create(String userId, String subject, long expireTime) {
        Claims claims = Jwts.claims().setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));
        claims.put("userId", userId);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 유효성 확인
    public boolean checkToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            System.out.println(claims.getBody().get("userId"));
            return true;
        } catch (Exception e) {
            System.err.println("token fail: " + e.getMessage());
            return false;
        }
    }

    // JWT에서 사용자 ID 추출 메서드
    public String getUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", String.class);  // 토큰에서 userId 클레임을 추출
        } catch (Exception e) {
            System.err.println("Failed to get userId from token: " + e.getMessage());
            return null; // 예외 발생 시 null 반환
        }
    }

    // RefreshToken 유효성 검사
    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Date expiration = claims.getBody().getExpiration();
            return !expiration.before(new Date());  // 만료되지 않았는지 확인
        } catch (Exception e) {
            System.err.println("RefreshToken check fail: " + e.getMessage());
            return false;
        }
    }
}
