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
import java.util.Optional;

public interface OfferService {

    Offer addNewOffer(OfferRequestDto request, String email);

    Page<OfferFilterResponseDto> getActiveOffersByCity(String cityName, Pageable pageable);

    List<OfferFilterResponseDto> getAllActiveOffers();

    Page<OfferFilterResponseDto> getAllActiveOffers(Pageable pageable);

    Page<OfferFilterResponseDto> getFilteredOffers(
            String cityName, String category, String keyPhrase,
            BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest);

    Optional<List<OfferFilterResponseDto>> getOfferByTitle(String title);

    Optional<OfferProfileGuestResponseDto> getActiveOfferById(Long id);

    Offer getActiveOfferEntityById(Long id);

    Offer updateOffer(OfferRequestDto dto, Long id);

    void deleteOfferById(Long id);

}



