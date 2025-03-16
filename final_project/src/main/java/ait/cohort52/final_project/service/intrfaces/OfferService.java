package ait.cohort52.final_project.service.intrfaces;

import ait.cohort52.final_project.domain.dto.requestDto.OfferRequestDto;
import ait.cohort52.final_project.domain.dto.responseDto.OfferResponseDto;
import ait.cohort52.final_project.domain.entity.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Offer addNewOffer(OfferRequestDto request);

    Optional<OfferResponseDto> getOfferByTitle(String title);

    Optional<OfferResponseDto> getOfferById(Long id);

    Offer updateOffer(OfferRequestDto dto, Long id);

    void deleteOffer(Long id);

    List<Offer> findAllOffers();


}



