package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Column(name = "name", nullable = false)
    private String title;

    @NotBlank(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @Column(nullable = false)
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private ImageGallery gallery;

    @Column(name = "active")
    private boolean active;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Offer() {
    }

    public Offer(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public ImageGallery getGallery() {
        return gallery;
    }

    public void setGallery(ImageGallery gallery) {
        this.gallery = gallery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer offer)) return false;
        return active == offer.active && Objects.equals(id, offer.id) && Objects.equals(title, offer.title) && Objects.equals(pricePerHour, offer.pricePerHour) && Objects.equals(description, offer.description) && Objects.equals(category, offer.category) && Objects.equals(gallery, offer.gallery) && Objects.equals(user, offer.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pricePerHour, description, category, gallery, active, user);
    }

    @Override
    public String toString() {
        return String.format("Offer: id - %d, title - %s, pricePerHour - %.2f, category - %s, gallery - %s, description - %s, active - %s", id, title, pricePerHour, category, gallery, description, active ? "Yes" : "No");
    }
}
