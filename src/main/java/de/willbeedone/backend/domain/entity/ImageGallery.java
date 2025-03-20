package de.willbeedone.backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "image_gallery")
public class ImageGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Long id;

    @ElementCollection
    @Column(name = "image_url", nullable = false)
    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Each image URL must be a valid URL"
    )
    private List<String> imageUrl;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageGallery that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(offer, that.offer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageUrl, offer);
    }

    @Override
    public String toString() {
        return String.format("ImageGallery: id - %d, imageUrl - %s", id, imageUrl);
    }

}
