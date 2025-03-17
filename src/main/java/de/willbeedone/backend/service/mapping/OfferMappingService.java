package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface OfferMappingService {


    OfferRequestDto mapRequestEntityToDto(Offer entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Offer mapDtoToRequestOffer(UserRequestDto dto);


    OfferResponseDto mapResponseEntityToDto(Offer entity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Offer mapDtoToResponseEntity(OfferRequestDto dto);
}
