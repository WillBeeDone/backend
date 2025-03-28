package de.willbeedone.backend.domain.dto.location_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Schema(description = "A class that defines the Location DTO for requests and responses.")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class LocationDto {

    @Schema(description = "User location", example = "Berlin")
    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$", message = "City name can only contain letters, spaces, and hyphens")
    private String cityName;

    @Override
    public String toString() {
        return String.format("LocationResponseDto: cityName - %s", cityName);
    }
}
