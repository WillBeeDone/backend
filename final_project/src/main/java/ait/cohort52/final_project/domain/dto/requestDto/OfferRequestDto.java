package ait.cohort52.final_project.domain.dto.requestDto;

import ait.cohort52.final_project.domain.entity.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestDto {
    private Long id;

    @NotNull(message = "Price per hour is required")
    private BigDecimal pricePerHour;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Category is required")
    private Category category;

    @NotBlank(message = "Title cannot be blank")
    private String title;
}
