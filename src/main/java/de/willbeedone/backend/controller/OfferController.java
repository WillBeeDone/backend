package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@Tag(name = "Offer controller", description = "Controller for various operations with Offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Getting all offers",
            description = "Returns all offers for the gallery.")
    @GetMapping("/all")
    public List<OfferFilterResponseDto> getAllOffers() {
        return offerService.getAllOffers();
    }

    @Operation(summary = "Getting offer by id",
            description = "Returns precise offer by id for its profile card.")
    @GetMapping("/{id}")
    public OfferProfileGuestResponseDto getActiveOfferByIdGuest(
            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long id) {
        return offerService.getActiveOfferById(id);
    }
}
