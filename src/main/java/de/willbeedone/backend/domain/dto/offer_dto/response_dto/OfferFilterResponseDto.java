package de.willbeedone.backend.domain.dto.offer_dto.response_dto;

import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import lombok.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class OfferFilterResponseDto {

    private Long id;
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    private Category category;
    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    private BigDecimal pricePerHour;
    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;
    private UserFilterResponseDto userFilterResponseDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfferFilterResponseDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(category, that.category) && Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(userFilterResponseDto, that.userFilterResponseDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, pricePerHour, description, userFilterResponseDto);
    }

    @Override
    public String toString() {
        return String.format("OfferFilterResponseDto: id - %d, title - %s, category - %s, pricePerHour - %.2f, description - %s, userFilterResponseDto - %s", id, title, category, pricePerHour, description, userFilterResponseDto);
    }
}

