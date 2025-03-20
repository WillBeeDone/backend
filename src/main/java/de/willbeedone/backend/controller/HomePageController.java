package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomePageController {

    private final OfferService offerService;

    public HomePageController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public List<OfferFilterResponseDto> getAllOffers() {
        return offerService.getAllOffers();
    }

}
