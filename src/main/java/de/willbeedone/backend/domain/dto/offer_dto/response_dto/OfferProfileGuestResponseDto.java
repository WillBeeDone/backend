package de.willbeedone.backend.domain.dto.offer_dto.response_dto;

import de.willbeedone.backend.domain.dto.category_dto.CategoryDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.ImageGallery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "A class that defines the Offer DTO for profile cards responses made by unauthorized users.")
public class OfferProfileGuestResponseDto {

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

    @Schema(description = "Offer categoryDto")
    private CategoryDto categoryDto;

    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @Schema(description = "Price per hour", example = "65.00")
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, max = 1500, message = "Description must be between 10 and 1000 characters")
    @Schema(description = "Offer detailed description")
    private String description;

    @Schema(description = "Photo gallery of work results")
    private Set<ImageGallery> images;

    @NotNull
    @Schema(description = "The fields from User: first name, last name, location, profile picture, email, phoneNumber")
    private UserProfileResponseDto userProfileResponseDto;

    @Override
    public String toString() {

        return String.format("OfferProfileGuestResponseDto: id - %d, title - %s, categoryResponseDto - %s, pricePerHour - %.2f, description - %s, images - %s, userProfileResponseDto - %s", id, title, categoryDto, pricePerHour, description, images, userProfileResponseDto);
    }
}
