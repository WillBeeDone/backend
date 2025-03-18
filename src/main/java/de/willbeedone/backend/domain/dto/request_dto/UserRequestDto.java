package de.willbeedone.backend.domain.dto.request_dto;

import de.willbeedone.backend.domain.entity.enums.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor


public class UserRequestDto {
    private String email;
    private String firstName;
    private String lastName;
    private Location location;
    private String password;
    private String profilePicture;
    private String phoneNumber;
    private boolean blocked;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        UserRequestDto that = (UserRequestDto) o;
        return blocked == that.blocked && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && location == that.location && Objects.equals(password, that.password) && Objects.equals(profilePicture, that.profilePicture) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, location, password, profilePicture, phoneNumber, blocked);
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location=" + location +
                ", password='" + password + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
