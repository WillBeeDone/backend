package de.willbeedone.backend.domain.dto.offer_dto.request_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "A class that defines the Offer DTO for requests")
public class OfferRequestDto {

    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @Schema(description = "Price per hour", example = "65.00")
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 3, max = 1500, message = "Description must be between 10 and 1000 characters")
    @Schema(description = "Offer detailed description")
    private String description;

    @NotNull
    @Schema(description = "Offer category name")
    private String categoryName;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Schema(description = "Short offer description", example = "Super sexy plumber will fix your pipes.")
    private String title;

    @Schema(description = "Photo gallery of work results")
    private Set<MultipartFile> images;

    @Override
    public String toString() {
        return String.format("Offer: title - %s, description - %s, pricePerHour - %.2f, categoryName - %s, images - %s", title, description, pricePerHour, categoryName, images);
    }
}
