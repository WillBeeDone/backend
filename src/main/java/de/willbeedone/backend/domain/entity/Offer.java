package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Price per hour cannot be empty")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    @Column(name = "price_per_hour")
    private BigDecimal pricePerHour;

    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @Column(name = "description", columnDefinition = "MEDIUMTEXT")
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ImageGallery> images;

    @NotNull
    @Column(name = "active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active;

    @NotNull
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return String.format("Offer: id - %d, title - %s, pricePerHour - %.2f, category - %s, gallery - %s, description - %s, active - %s", id, title, pricePerHour, category, images, description, active ? "Yes" : "No");
    }
}
