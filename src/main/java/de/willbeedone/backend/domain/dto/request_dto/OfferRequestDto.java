package de.willbeedone.backend.domain.dto.request_dto;
import de.willbeedone.backend.domain.entity.Category;
import de.willbeedone.backend.domain.entity.enums.Location;
import lombok.*;



import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


public class OfferRequestDto {

    private BigDecimal pricePerHour;

    private String description;

    private Category category;

    private String title;

    private Location location;

    private List<String> gallery;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OfferRequestDto that = (OfferRequestDto) o;
        return Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && location == that.location && Objects.equals(gallery, that.gallery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricePerHour, description, category, title, location, gallery);
    }

    public OfferRequestDto() {
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    @Override
    public String toString() {
        return String.format("Offer with title %s, description %s, pricePerHour %.2f", title, description, pricePerHour);
    }
}
