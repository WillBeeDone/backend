package de.willbeedone.backend.domain.dto.offer_dto.response_dto;

import de.willbeedone.backend.domain.dto.category_dto.CategoryDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "A class that defines the Offer DTO for filtering responses (Category, Location, Search field) in the offers gallery on the Home Page.")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class OfferFilterResponseDto {

    @NotNull
    @Schema(
            description = "Offer unique identifier",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Schema(description = "Short offer description", example = "Super sexy plumber will fix your pipes.")
    private String title;

    @NotNull
    @Schema(description = "Offer categoryDto")
    private CategoryDto categoryDto;

    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @Schema(description = "Price per hour", example = "65.00")
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @Schema(description = "Offer detailed description")
    private String description;

    @NotNull
    @Schema(description = "The fields from User: first name, last name, location, profile picture")
    private UserFilterResponseDto userFilterResponseDto;

    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "user@example.com")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
    @Schema(description = "User phone number", example = "+1234567890")
    private String phoneNumber;

    @Override
    public String toString() {
        return String.format(
                "OfferFilterResponseDto: id - %d, title - %s, categoryResponseDto - %s, pricePerHour - %.2f, description - %s, userFilterResponseDto - %s, email - %s, phoneNumber - %s",
                id, title, categoryDto, pricePerHour, description, userFilterResponseDto, email, phoneNumber
        );
    }
}

