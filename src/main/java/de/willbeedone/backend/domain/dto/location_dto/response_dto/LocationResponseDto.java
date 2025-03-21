package de.willbeedone.backend.domain.dto.location_dto.response_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Schema(description = "A class that defines the Location DTO for responses.")
@Getter
@Setter
@NoArgsConstructor
public class LocationResponseDto {

    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$", message = "City name can only contain letters, spaces, and hyphens")
    private String cityName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationResponseDto that)) return false;
        return Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cityName);
    }

    @Override
    public String toString() {
        return String.format("LocationResponseDto: cityName - %s", cityName);
    }
}
