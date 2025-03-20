package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.entity.Location;

import java.util.Objects;

public class UserFilterResponseDto {

    private String firstName;
    private String lastName;
    private String profilePicture;
    private Location location;

    public UserFilterResponseDto() {
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFilterResponseDto that)) return false;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(profilePicture, that.profilePicture) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, profilePicture, location);
    }

    @Override
    public String toString() {
        return String.format("UserFilterResponseDto: firstName - %s, lastName - %s, profilePicture - %s, location - %s", firstName, lastName, profilePicture, location);
    }
}
