package de.willbeedone.backend.domain.dto.request_dto;


import de.willbeedone.backend.domain.entity.enums.Category;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestDto {
    private Long id;

//    @NotNull(message = "Price per hour is required")
    private BigDecimal pricePerHour;

    private String description;

//    @NotNull(message = "Category is required")
    private Category category;

    private String title;
}
