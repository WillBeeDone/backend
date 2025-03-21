package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.category_dto.response_dto.CategoryResponseDto;
import de.willbeedone.backend.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMappingService {

    CategoryResponseDto mapEntityToResponseDto(Category entity);

}
