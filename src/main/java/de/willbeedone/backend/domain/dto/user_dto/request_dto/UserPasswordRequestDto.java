package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserPasswordRequestDto {

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\d)(?=.*[^\\p{L}\\p{N}\\s]).{8,}$",
            message = "Password must be at least 8 characters, contain uppercase, lowercase, a digit, and a special symbol, and can include German letters.")
    @Schema(description = "User's password", example = "abHh7$_R")
    private String password;

    @Override
    public String toString() {
        return String.format("UserPasswordRequestDto: password - %s", password);
    }

}
