package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "active")
    private boolean active;

    @Column(name = "location")
    private String location;

    @Column(name = "name", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ElementCollection
    @JsonIgnore
    private List<String> gallery;

    public Offer() {
    }

    public Offer(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return active == offer.active && Objects.equals(id, offer.id) && Objects.equals(location, offer.location) && Objects.equals(title, offer.title) && Objects.equals(user, offer.user) && Objects.equals(pricePerHour, offer.pricePerHour) && Objects.equals(description, offer.description) && Objects.equals(category, offer.category) && Objects.equals(gallery, offer.gallery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, location, title, user, pricePerHour, description, category, gallery);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", active=" + active +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", pricePerHour=" + pricePerHour +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", gallery=" + gallery +
                '}';
    }
}