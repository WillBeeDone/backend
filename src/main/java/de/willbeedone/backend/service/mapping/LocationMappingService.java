package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.location_dto.LocationDto;
import de.willbeedone.backend.domain.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMappingService {

    LocationDto mapEntityToResponseDto(Location entity);

}
