package de.willbeedone.backend.domain.dto.offer_dto.response_dto;

import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserFilterResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.ImageGallery;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

public class OfferProfileGuestResponseDto {

    private Long id;
    private String title;
    private Category category;
    private BigDecimal pricePerHour;
    private String description;
    private ImageGallery gallery;
    private UserFilterResponseDto userFilterResponseDto;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OfferProfileGuestResponseDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(category, that.category) && Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(gallery, that.gallery) && Objects.equals(userFilterResponseDto, that.userFilterResponseDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, category, pricePerHour, description, gallery, userFilterResponseDto);
    }

    @Override
    public String toString() {

        return String.format("OfferProfileGuestResponseDto: id - %d, title - %s, category - %s, pricePerHour - %.2f, description - %s, gallery - %s, userFilterResponseDto - %s", id, title, category, pricePerHour, description, gallery, userFilterResponseDto);
    }
}
