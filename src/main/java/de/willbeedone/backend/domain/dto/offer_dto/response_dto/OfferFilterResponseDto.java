package de.willbeedone.backend.domain.dto.offer_dto.response_dto;

import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class OfferFilterResponseDto {

    private Long id;
    private String title;
    private Category category;
    private BigDecimal pricePerHour;
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

