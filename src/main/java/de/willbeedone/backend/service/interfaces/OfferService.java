package de.willbeedone.backend.service.interfaces;



import de.willbeedone.backend.domain.dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.response_dto.OfferResponseDto;
import de.willbeedone.backend.domain.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Offer addNewOffer(OfferRequestDto request);

    List<OfferResponseDto> getOfferByTitle(String title);

    Optional<OfferResponseDto> getOfferById(Long id);

    Offer updateOffer(OfferRequestDto dto, Long id);

    void deleteOfferById(Long id);

    List<Offer> findAllOffers();


}



