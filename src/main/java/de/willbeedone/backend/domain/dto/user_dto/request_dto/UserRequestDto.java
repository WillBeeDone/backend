package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequestDto {

    @NotBlank(message = "E-mail cannot be empty")
    @Email(message = "Invalid e-mail format")
    @Schema(description = "User's e-mail", example = "john@gmail.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Schema(description = "User's password", example = "11111111")
    private String password;

    @Override
    public String toString() {
        return String.format("UserRequestDto: email - %s, password - %s", email, password);
    }
}
