package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    Offer addNewOffer(OfferRequestDto request);

    List<OfferFilterResponseDto> getAllOffers();

    List<OfferFilterResponseDto> getFilteredOffers(String cityName, String category, String keyPhrase);

    Optional<List<OfferFilterResponseDto>> getOfferByTitle(String title);

    OfferProfileGuestResponseDto getActiveOfferById(Long id);

    Offer getActiveOfferEntityById(Long id);

    Offer updateOffer(OfferRequestDto dto, Long id);

    void deleteOfferById(Long id);

}



