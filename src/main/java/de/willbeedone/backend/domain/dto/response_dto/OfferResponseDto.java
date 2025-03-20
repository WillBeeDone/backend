package de.willbeedone.backend.domain.dto.response_dto;

import java.math.BigDecimal;
import java.util.Objects;


public class OfferResponseDto {

    private Long id;
    private BigDecimal pricePerHour;
    private String description;
//    private Category category;
    private String title;

    public OfferResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OfferResponseDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OfferResponseDto that = (OfferResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(pricePerHour, that.pricePerHour) && Objects.equals(description, that.description) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pricePerHour, description, title);
    }

    @Override
    public String toString() {
        return "OfferResponseDto{" +
                "id=" + id +
                ", pricePerHour=" + pricePerHour +
                ", description='" + description + '\'' +
//                ", category=" + category +
                ", title='" + title + '\'' +
                '}';
    }
}

