package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.dto.location_dto.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Schema(description = "A class that defines the User DTO for responses.")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserFilterResponseDto {

    @NotBlank
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters")
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotBlank
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters")
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @NotBlank
    @Schema(description = "User's profile photo")
    private String profilePicture;

    @NotNull
    @Schema(description = "User's city")
    private LocationDto locationDto;

    @Override
    public String toString() {
        return String.format("UserFilterResponseDto: firstName - %s, lastName - %s, profilePicture - %s, locationResponseDto - %s", firstName, lastName, profilePicture, locationDto);
    }
}
