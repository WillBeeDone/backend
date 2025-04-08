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
    @Pattern(
            regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Password must not contain spaces, must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, one digit, and one special character."
    )
    @Schema(description = "User's password", example = "abHh7$_R")
    private String password;

    @Override
    public String toString() {
        return String.format("UserRequestDto: email - %s, password - %s", email, password);
    }
}
