package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.dto.location_dto.response_dto.LocationResponseDto;
import de.willbeedone.backend.domain.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "A class that defines the User DTO for responses.")
public class UserFilterResponseDto {

    @NotBlank(message = "First name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters")
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters")
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Schema(description = "User's profile photo")
    private String profilePicture;

    @Schema(description = "User's city", example = "Berlin")
    private LocationResponseDto locationResponseDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFilterResponseDto that)) return false;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(profilePicture, that.profilePicture) && Objects.equals(locationResponseDto, that.locationResponseDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, profilePicture, locationResponseDto);
    }

    @Override
    public String toString() {
        return String.format("UserFilterResponseDto: firstName - %s, lastName - %s, profilePicture - %s, locationResponseDto - %s", firstName, lastName, profilePicture, locationResponseDto);
    }
}
