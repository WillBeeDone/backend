package de.willbeedone.backend.security.sec_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Schema(description = "A class that defines the Refresh Dto for requests.")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RefreshRequestDto {

    @Schema(description = "User's refresh token")
    @NotBlank
    private String refreshToken;

    @Override
    public String toString() {
        return String.format("RefreshRequestDTO: refreshToken - %s", refreshToken);
    }

}
