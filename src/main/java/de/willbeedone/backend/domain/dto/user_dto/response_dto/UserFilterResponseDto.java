package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.entity.Location;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserFilterResponseDto {

    private String firstName;
    private String lastName;
    private String profilePicture;
    private Location location;

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
