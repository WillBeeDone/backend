package de.willbeedone.backend.domain.dto.response_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;


public class UserResponseDto {
    public UserResponseDto() {
    }

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean blocked;

    public UserResponseDto(Long id, @NotBlank(message = "Email cannot be empty or null") String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseDto that = (UserResponseDto) o;
        return blocked == that.blocked && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, blocked);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
