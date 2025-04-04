package de.willbeedone.backend.domain.dto.category_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;


@Schema(description = "A class that defines the Category DTO for requests and responses.")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto {

    @NotBlank
    @Schema(description = "Offer category", example = "Plumber")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s-]{3,50}$", message = "Name must be between 3 and 50 characters and contain only letters, numbers, spaces, or hyphens")
    private String name;

    @Override
    public String toString() {
        return String.format("CategoryResponseDto: name - %s", name);
    }
}
