package de.willbeedone.backend.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@Entity
@EqualsAndHashCode
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

    @NotBlank
    @Column(name = "image_url")
    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Each image URL must be a valid URL"
    )
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Override
    public String toString() {
        return String.format("ImageGallery: id - %d, imageUrl - %s", id, imageUrl);
    }

}
