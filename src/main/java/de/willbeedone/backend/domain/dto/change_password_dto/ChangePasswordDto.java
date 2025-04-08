package de.willbeedone.backend.domain.dto.change_password_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "A class that defines the Change Password DTO for password change requests.")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ChangePasswordDto {

    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Password must not contain spaces, must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, one digit, and one special character."
    )
    @Schema(description = "User's password", example = "abHh7$_R")
    private String oldPassword;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(
            regexp = "^(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Password must not contain spaces, must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, one digit, and one special character."
    )
    @Schema(description = "User's password", example = "abHh7$_R")
    private String newPassword;

    @Override
    public String toString() {
        return String.format("ChangePasswordDto: oldPassword - %s, newPassword - %s", oldPassword, newPassword);
    }
}
