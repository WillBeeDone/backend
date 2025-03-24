package de.willbeedone.backend.security.sec_service;

import de.willbeedone.backend.domain.entity.Role;
import de.willbeedone.backend.exceptions.custom_exceptions.TokenValidationException;
import de.willbeedone.backend.repository.RoleRepository;
import de.willbeedone.backend.security.AuthInfo;
import de.willbeedone.backend.security.sec_dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
@Service
@Getter
@NoArgsConstructor
public class TokenService {

    private SecretKey accessKey;
    private SecretKey refreshKey;
    private RoleRepository roleRepository;

    public TokenService(
            @Value("${key.access}") String accessPhrase,
            @Value("${key.refresh}") String refreshPhrase,
            RoleRepository roleRepository) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshPhrase));

        this.roleRepository = roleRepository;
    }

    public String generateAccessToken(CustomUserDetails user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expiration = currentDate.plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getAuthorities())
                .claim("name", user.getEmail())
                .compact();
    }

    public String generateRefreshToken(CustomUserDetails user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expiration = currentDate.plusWeeks(7).atZone(ZoneId.systemDefault()).toInstant();
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getEmail())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    public boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (TokenValidationException e) {
            return false;
        }
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    public Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        String email = claims.getSubject();
        List<LinkedHashMap<String, String>> rolesList =
                (List<LinkedHashMap<String, String>>) claims.get("roles");
        Set<Role> roles = new HashSet<>();

        for (LinkedHashMap<String, String> roleEntry : rolesList) {
            String roleTitle = roleEntry.get("authority");
            roleRepository.findByTitle(roleTitle).ifPresent(roles::add);
        }
        return new AuthInfo(email, roles);
    }
}
