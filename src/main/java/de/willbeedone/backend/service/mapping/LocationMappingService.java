package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.location_dto.response_dto.LocationResponseDto;
import de.willbeedone.backend.domain.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMappingService {

    LocationResponseDto mapEntityToResponseDto(Location entity);

}
