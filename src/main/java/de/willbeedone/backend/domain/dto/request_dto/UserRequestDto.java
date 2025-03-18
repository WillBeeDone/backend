package de.willbeedone.backend.domain.dto.request_dto;

import java.util.Objects;

public class UserRequestDto {

    private String email;
    private String password;
    private boolean blocked;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

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
