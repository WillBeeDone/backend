package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public List<OfferFilterResponseDto> getAllOffers() {
        return offerService.getAllOffers();
    }

    @GetMapping("/{id}")
    public OfferProfileGuestResponseDto getAllOffersForGuest(@PathVariable Long id) {
        return offerService.getActiveOfferById(id);
    }
}
