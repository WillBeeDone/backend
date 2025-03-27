package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.category_dto.CategoryDto;
import de.willbeedone.backend.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMappingService {

    CategoryDto mapEntityToResponseDto(Category entity);

}
