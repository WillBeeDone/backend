package de.willbeedone.backend.domain.dto.offer_dto.request_dto;

import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.ImageGallery;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class OfferRequestDto {

    private BigDecimal pricePerHour;
    private String description;
    private Category category;
    private String title;
    private ImageGallery gallery;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfferRequestDto that)) return false;
        return Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(gallery, that.gallery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerHour, description, category, title, gallery);
    }

    @Override
    public String toString() {
        return String.format("Offer: title - %s, description - %s, pricePerHour - %.2f, category - %s, gallery - %s", title, description, pricePerHour, category, gallery);
    }
}
