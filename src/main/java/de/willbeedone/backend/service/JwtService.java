package de.willbeedone.backend.service;

import de.willbeedone.backend.domain.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String jwtSecret;

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseJwt(String jwtToken) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret) // Установите ключ подписи
                .build();

        // Парсинг и валидация
        Jws<Claims> jws = parser.parseClaimsJws(jwtToken);
        return jws.getBody(); // Возвращает тело токена (Claims)
    }

    public String generateToken(User user) {
        long expirationTime = 1000 * 60 * 60 * 24; // 24 часа
        return Jwts.builder()
                .setSubject(user.getEmail()) // Используем email пользователя
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }


    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(refreshToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

        // Извлечение email из токена
        public String extractEmailFromToken(String token, boolean isRefreshToken) {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        }


    public String generateAccessToken(String email) {
        long expirationTime = 1000 * 60 * 60; // 1 час
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Вспомогательный метод для получения claims из токена
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

