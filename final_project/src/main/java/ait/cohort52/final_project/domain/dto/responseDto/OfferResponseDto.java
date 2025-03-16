package ait.cohort52.final_project.domain.dto.responseDto;



import ait.cohort52.final_project.domain.entity.enums.Category;
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

