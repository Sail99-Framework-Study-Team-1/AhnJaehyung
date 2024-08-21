package com.example.demo.jwt;

import com.example.demo.domain.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        String algorithm = Jwts.SIG.HS256.key().build().getAlgorithm();
        this.secretKey = new SecretKeySpec(key, algorithm);
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration().before(new Date());
    }

    public String createToken(User user, long expiredMs) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .claim("id", user.getId())
                .issuedAt(new Date(currentTime))
                .expiration(new Date(currentTime + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
