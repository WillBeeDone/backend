package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequestDto {

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private boolean blocked;

    @Override
    public String toString() {
        return String.format("UserRequestDto: email - %s, password - %s, blocked - %s", email, password, blocked ? "Yes" : "No");
    }
}
