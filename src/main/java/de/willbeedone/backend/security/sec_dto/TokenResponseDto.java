package de.willbeedone.backend.security.sec_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode

public class TokenResponseDto {

    private String accessToken;
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
