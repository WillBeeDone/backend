package de.willbeedone.backend.security.sec_controller;

import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.security.sec_dto.RefreshRequestDto;
import de.willbeedone.backend.security.sec_dto.TokenResponseDto;
import de.willbeedone.backend.security.sec_service.AuthService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody User user) {
        try {
            return service.login(user);
        }catch (AuthException e){
            return new TokenResponseDto(null);
        }
    }
    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto refreshRequest){
return service.getNewAccessToken(refreshRequest.getRefreshToken());
    }
}
