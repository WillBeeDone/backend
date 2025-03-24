package de.willbeedone.backend.domain.dto.offer_dto.request_dto;

import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.ImageGallery;
import lombok.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OfferRequestDto {

    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;

    private Category category;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    private Set<ImageGallery> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfferRequestDto that)) return false;
        return Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(images, that.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerHour, description, category, title, images);
    }

    @Override
    public String toString() {
        return String.format("Offer: title - %s, description - %s, pricePerHour - %.2f, category - %s, images - %s", title, description, pricePerHour, category, images);
    }
}
