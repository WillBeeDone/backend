package de.willbeedone.backend.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "A class that defines the Token Dto for responses.")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class TokenResponseDto {

    @Schema(description = "User's access token")
    private String accessToken;

    @Schema(description = "User's refresh token")
    private String refreshToken;

    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return String.format("Token Response DTO:Access Token - %s, Refresh Token - %s", accessToken, refreshToken);
    }
}
