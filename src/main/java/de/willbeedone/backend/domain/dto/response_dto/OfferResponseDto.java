package de.willbeedone.backend.domain.dto.response_dto;




import de.willbeedone.backend.domain.entity.enums.Category;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferResponseDto {
    private Long id;

    private BigDecimal pricePerHour;

    private String description;

    private Category category;

    private String title;


}

