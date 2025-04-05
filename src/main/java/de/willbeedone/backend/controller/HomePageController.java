package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Controller for the Home Page", description = "Controller for various operations on the Home Page.")
public class HomePageController {

    private final OfferService offerService;

    public HomePageController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Getting all pageable active offers",
            description = "Returns all pageable active offers for the gallery. Default size - 12.")
    @GetMapping("/")
    public Page<OfferFilterResponseDto> getAllActiveOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return offerService.getAllActiveOffers(pageable);
    }

}
