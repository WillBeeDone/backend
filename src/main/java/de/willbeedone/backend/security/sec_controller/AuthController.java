package de.willbeedone.backend.security.sec_controller;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserLoginResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import de.willbeedone.backend.security.sec_dto.RefreshRequestDto;
import de.willbeedone.backend.security.sec_dto.TokenResponseDto;
import de.willbeedone.backend.security.sec_service.AuthService;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication controller", description = "Controller for login (user gets access and refresh tokens) and reset of access token.")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMappingService userMappingService;

    public AuthController(AuthService authService, UserService userService, UserMappingService userMappingService) {
        this.authService = authService;
        this.userService = userService;
        this.userMappingService = userMappingService;
    }

    @Operation(summary = "User login",
            description = "Generates and sends access and refresh tokens for user.")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserRequestDto requestDto) throws AuthException {

            TokenResponseDto tokenResponseDto = authService.login(requestDto);
            User userEntity = userService.getUserByEmail(requestDto.getEmail())
                    .orElseThrow(
                            UserNotFoundException::new
                    );

            UserLoginResponseDto userLoginResponseDto = userMappingService.mapEntityToLoginResponseDto(userEntity);
            userLoginResponseDto.setAccessToken(tokenResponseDto.getAccessToken());
            userLoginResponseDto.setRefreshToken(tokenResponseDto.getRefreshToken());
            userLoginResponseDto.setRoles(userEntity.getRoles());

            return userLoginResponseDto;
    }

    @Operation(summary = "Reset access token",
            description = "Generates and sends new access token when the previous one is expired.")
    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto refreshRequest){
        return authService.getNewAccessToken(refreshRequest.getRefreshToken());
    }
}
