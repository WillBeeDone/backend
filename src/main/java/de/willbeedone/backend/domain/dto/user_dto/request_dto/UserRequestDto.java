package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")

    private String password;

    private boolean blocked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequestDto that)) return false;
        return blocked == that.blocked && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, blocked);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRequestDto{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", blocked=").append(blocked);
        sb.append('}');
        return sb.toString();
    }
}
