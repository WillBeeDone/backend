package de.willbeedone.backend.domain.dto.user_dto.request_dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    private String email;
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
