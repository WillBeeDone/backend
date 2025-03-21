package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Controller for the Home Page", description = "Controller for various operations on the Home Page")
public class HomePageController {

    private final OfferService offerService;

    public HomePageController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Getting all offers",
            description = "Returns all offers for the gallery on the Home Page.")
    @GetMapping("/")
    public List<OfferFilterResponseDto> getAllOffers() {
        return offerService.getAllOffers();
    }

}
