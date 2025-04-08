package de.willbeedone.backend.security.sec_controller;

import de.willbeedone.backend.domain.dto.change_password_dto.ChangePasswordDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserPasswordRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserLoginResponseDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.exceptions.custom_exceptions.PasswordException;
import de.willbeedone.backend.security.sec_dto.RefreshRequestDto;
import de.willbeedone.backend.security.sec_dto.TokenResponseDto;
import de.willbeedone.backend.security.sec_service.AuthService;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.UserService;
import de.willbeedone.backend.service.mapping.UserMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication controller", description = "Controller for login (user gets access and refresh tokens) and reset of access token.")
public class AuthController {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final TokenService tokenService;
    @Autowired
    private final UserMappingService userMappingService;

    public AuthController(AuthService authService, UserService userService, TokenService tokenService, UserMappingService userMappingService) {
        this.authService = authService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMappingService = userMappingService;
    }

    @Operation(summary = "User login",
            description = "Generates and sends access and refresh tokens for user.")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserRequestDto requestDto) throws AuthException {

            TokenResponseDto tokenResponseDto = authService.login(requestDto);
            User userEntity = userService.getActiveValidUserByEmail(requestDto.getEmail());

            UserLoginResponseDto userLoginResponseDto = userMappingService.mapEntityToLoginResponseDto(userEntity);
            userLoginResponseDto.setAccessToken(tokenResponseDto.getAccessToken());
            userLoginResponseDto.setRefreshToken(tokenResponseDto.getRefreshToken());
            userLoginResponseDto.setRoles(userEntity.getRoles());

            return userLoginResponseDto;
    }

    @Operation(summary = "Request for password reset",
            description = "Sends e-mail for password reset to user if he has forgotten his password.")
    @PostMapping("/reset")
    public Response forgotPassword(@RequestBody UserEmailRequestDto dto) throws AuthException {
        userService.forgotPassword(dto);
        return new Response("OK");
    }

    @Operation(summary = "Password reset",
            description = "Resets user's password.")
    @PostMapping("/reset/{code}")
    public Response confirmRegistration(
            @PathVariable String code,
            @RequestBody UserPasswordRequestDto dto
            ) {
        userService.resetPassword(code, dto);
        return new Response("OK");
    }

    @Operation(summary = "Password change",
            description = "Changes user's password.")
    @PutMapping("/change")
    public Response changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDto changePasswordDto
    ) {
        if (changePasswordDto.getOldPassword().equals(changePasswordDto.getNewPassword())) {
            throw new PasswordException("New password must differ from the old password.");
        }
        String email = tokenService.extractEmailFromToken(token);
        userService.changePassword(changePasswordDto, email);
        return new Response("OK");
    }

    @Operation(summary = "Reset access token",
            description = "Generates and sends new access token when the previous one is expired.")
    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto refreshRequest){
        return authService.getNewAccessToken(refreshRequest.getRefreshToken());
    }

}