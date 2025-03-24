package de.willbeedone.backend.security.sec_dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RefreshRequestDto {

    private String refreshToken;

    @Override
    public String toString() {
        return String.format("Refresh Request DTO: refreshToken - %s",refreshToken);
    }
}
