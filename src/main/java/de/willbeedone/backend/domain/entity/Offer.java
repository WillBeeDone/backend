package de.willbeedone.backend.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter


public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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

    @Column(nullable = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @JsonIgnore
    private List<String> gallery;

    public Offer(Object o, String title, String description, boolean active) {
        this.title = title;
        this.description = description;
        this.active = active;
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



