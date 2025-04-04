package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.dto.location_dto.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Schema(description = "A class that defines the User DTO for his profile responses.")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserProfileResponseDto {

    @NotBlank
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    )
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotBlank
    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "Last name should start with a capital letter and contain only letters"
    )
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @NotBlank
    @Email
    @Schema(description = "User's e-mail", example = "john@gmail.com")
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    )
    @Schema(description = "User's phone number")
    private String phoneNumber;

    @NotNull
    @Schema(description = "User's city")
    private LocationDto locationDto;

    @NotBlank
    @Schema(description = "User's profile photo")
    private String profilePicture;

    @Override
    public String toString() {
        return String.format("UserProfileResponseDto: firstName - %s, lastName - %s, email - %s, phoneNumber - %s, locationDto - %s, profilePicture - %s", firstName, lastName, email, phoneNumber, locationDto, profilePicture);
    }

}
