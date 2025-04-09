package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.JwtAuthenticationResponse;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.repository.UserRepository;
import de.willbeedone.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("googleAuthController")
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientSecret;

    @Value("${GOOGLE_REDIRECT_URI}")
    private String googleRedirectUri;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody UserRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        // Генерируем токен
        String jwt = jwtService.generateToken(user);

        // Возвращаем токен клиенту
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");

        if (jwtService.validateRefreshToken(refreshToken)) {
            String email = jwtService.extractEmailFromToken(refreshToken, true);
            String newAccessToken = jwtService.generateAccessToken(email);
            return ResponseEntity.ok(Map.of("access_token", newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @GetMapping("/success")
    public String authSuccess(@RequestParam String accessToken, @RequestParam String refreshToken) {
        System.out.println("=== AUTH SUCCESS ===");
        return "✅ Авторизация прошла успешно!\n\n" +
                "🔐 Access token:\n" + accessToken + "\n\n" +
                "♻️ Refresh token:\n" + refreshToken;
    }
}
