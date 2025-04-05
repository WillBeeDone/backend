package de.willbeedone.backend.security.sec_service;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.security.sec_dto.TokenResponseDto;
import de.willbeedone.backend.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final Map<String, String> refreshStorage;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserService userService, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.refreshStorage = new HashMap<>();
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDto login(UserRequestDto inboundUser) throws AuthException {
        String email = inboundUser.getEmail();
        UserDetails foundUser = userService.loadUserByUsername(email);

        if (!foundUser.isEnabled()) {
            throw new AuthException("User is not activated");
        }

        if (foundUser.isAccountNonLocked()) {
            throw new AuthException("User is blocked");
        }

        if (passwordEncoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            String accessToken = tokenService.generateAccessToken((User) foundUser);
            String refreshToken = tokenService.generateRefreshToken(foundUser);
            refreshStorage.put(email, refreshToken);
            return new TokenResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Invalid credentials");
        }
    }

    public TokenResponseDto getNewAccessToken(String inboundRefreshToken){
        Claims refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);
        String email = refreshClaims.getSubject();
        String foundRefreshToken = refreshStorage.get(email);

        if(foundRefreshToken != null && foundRefreshToken.equals(inboundRefreshToken)){
            UserDetails foundUser = userService.loadUserByUsername(email);
            String accessToken = tokenService.generateAccessToken((User) foundUser);
            return new TokenResponseDto(accessToken);
        } else {
            return new TokenResponseDto(null);
        }
    }

}
