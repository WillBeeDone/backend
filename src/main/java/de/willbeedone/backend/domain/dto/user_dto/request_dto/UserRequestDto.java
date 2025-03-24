package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "first_name", nullable = true)
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    )
    @Size(min = 1, message = "First name should contain at least one character")
    private String firstName;

    @Column(name = "last_name", nullable = true)
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    )
    @Size(min = 1, message = "First name should contain at least one character")
    private String lastName;

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
