package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.image_gallery_dto.ImageGalleryDto;
import de.willbeedone.backend.domain.entity.ImageGallery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageGalleryMappingService {

    @Mapping(target = "id", ignore = true)
    ImageGallery mapRequestDtoToEntity(ImageGalleryDto dto);

}
