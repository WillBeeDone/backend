package de.willbeedone.backend.domain.dto.user_dto.response_dto;

import de.willbeedone.backend.domain.dto.location_dto.LocationDto;
import de.willbeedone.backend.domain.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Schema(description = "A class that defines the User DTO for registration responses.")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserLoginResponseDto {

    @NotNull
    @Schema(
            description = "User's unique identifier",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Pattern(
            regexp = "^[A-Z][a-zA-Z]{1,}$",
            message = "First name should start with a capital letter and contain only letters"
    )
    @Schema(description = "User's first name", example = "John")
    private String firstName;

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

    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "Phone number should contain only digits and can start with +"
    )
    @Schema(description = "User's phone number")
    private String phoneNumber;

    @Schema(description = "User's city")
    private LocationDto locationDto;

    @Schema(description = "User's profile photo")
    private String profilePicture;

    @Schema(description = "List of user's roles")
    private Set<Role> roles;

    @NotBlank
    @Schema(description = "User's activity status")
    private boolean active;

    @NotBlank
    @Schema(description = "User's block status")
    private boolean blocked;

    @Schema(description = "User's access token")
    private String accessToken;

    @Schema(description = "User's refresh token")
    private String refreshToken;

}
