package de.willbeedone.backend.service.mapping;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMappingService.class, CategoryMappingService.class})
public interface OfferMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    Offer mapRequestDtoToEntity(OfferRequestDto dto);

    @Mapping(source = "user", target = "userFilterResponseDto")
    @Mapping(source = "category", target = "categoryResponseDto")
    OfferFilterResponseDto mapEntityToFilterResponseDto(Offer entity);

    @Mapping(source = "user", target = "userFilterResponseDto")
    @Mapping(source = "category", target = "categoryResponseDto")
    OfferProfileGuestResponseDto mapEntityToProfileGuestResponseDto(Offer entity);
}