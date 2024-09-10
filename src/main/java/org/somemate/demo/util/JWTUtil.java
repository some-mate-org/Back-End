package org.somemate.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    private String salt = "somemate-secret-key-20240905-somemate-secret-key-20240905-somemate-secret-key-20240905";
    private long accessTokenExpireTime = 3600000; //1시간
    private long refreshTokenExpireTime = Duration.ofDays(30).toMillis();

    public String createAccessToken(int idx) {
        return create(idx, "access-token", accessTokenExpireTime);
    }

    public String createRefreshToken(int idx) {
        return create(idx, "refresh-token", refreshTokenExpireTime);
    }

    public String create(int idx, String subject, long expireTime) {
        Claims claims = Jwts.claims().setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));

        claims.put("idx", idx);

        String jwt = Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.ES256, this.generateKey())
                .compact();

        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Making JWT Key Error ::: "+ e.getMessage());
//			}
        }
        return key;
    }

    public boolean checkToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
            System.out.println("claims: "+ claims);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public int getUserIdx(String authorization) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(authorization);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        Map<String, Object> value = claims.getBody();
        System.out.println("value : " + value);
        return (Integer) value.get("idx");
    }
}
