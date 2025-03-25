package de.willbeedone.backend.domain.dto.category_dto.response_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Schema(description = "A class that defines the Category DTO for responses.")
@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {

    @Schema(description = "Offer category", example = "Plumber")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s-]{3,50}$", message = "Name must be between 3 and 50 characters and contain only letters, numbers, spaces, or hyphens")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryResponseDto that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return String.format("CategoryResponseDto: name - %s", name);
    }
}
