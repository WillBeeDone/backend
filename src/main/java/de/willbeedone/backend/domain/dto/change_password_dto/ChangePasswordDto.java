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
    @Pattern(regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\d)(?=.*[^\\p{L}\\p{N}\\s]).{8,}$",
            message = "Password must be at least 8 characters, contain uppercase, lowercase, a digit, and a special symbol, and can include German letters.")
    @Schema(description = "User's password", example = "abHh7$_R")
    private String oldPassword;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*\\p{Ll})(?=.*\\p{Lu})(?=.*\\d)(?=.*[^\\p{L}\\p{N}\\s]).{8,}$",
            message = "Password must be at least 8 characters, contain uppercase, lowercase, a digit, and a special symbol, and can include German letters.")
    @Schema(description = "User's password", example = "abHh7$_R")
    private String newPassword;

    @Override
    public String toString() {
        return String.format("ChangePasswordDto: oldPassword - %s, newPassword - %s", oldPassword, newPassword);
    }
}
