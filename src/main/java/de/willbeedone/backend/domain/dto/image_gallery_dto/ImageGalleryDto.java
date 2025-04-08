package de.willbeedone.backend.domain.dto.image_gallery_dto;

import de.willbeedone.backend.domain.entity.Offer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Schema(description = "A class that defines the ImageGallery DTO for requests and responses.")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ImageGalleryDto {

    @NotBlank
    @Pattern(
            regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
            message = "Each image URL must be a valid URL"
    )
    private String imageUrl;

    private Offer offer;

    @Override
    public String toString() {
        return String.format("ImageGalleryDto: name - %s", imageUrl);
    }

}
