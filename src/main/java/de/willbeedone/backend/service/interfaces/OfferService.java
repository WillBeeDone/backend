package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface OfferService {

    Offer addNewOffer(OfferRequestDto request, String email);

    void deactivateOfferById(String email, Offer offer);

    void activateOfferById(String email, Offer offer);

    void deleteOfferById(String email, Long offerId);

    List<OfferFilterResponseDto> getAllActiveOffers();

    Page<OfferFilterResponseDto> getAllActiveOffers(Pageable pageable);

    Page<OfferFilterResponseDto> getFilteredOffers(
            String cityName, String category, String keyPhrase,
            BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest);

    OfferProfileGuestResponseDto getActiveOfferById(Long offerId, String token);

    Offer getOfferEntityById(Long offerId);

    Offer getActiveOfferEntityById(Long offerId);

}



