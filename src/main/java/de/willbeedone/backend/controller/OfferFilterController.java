package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
@Tag(name = "Offers' filter controller", description = "Controller for various filtering operations with Offers")
public class OfferFilterController {

    private final OfferService service;

    public OfferFilterController(OfferService service) {
        this.service = service;
    }

    @Operation(summary = "Getting filtered offers",
            description = "Returns offers filtered by Category, Location or Key phrase from searching field. Filtration can include all, part or none of theese fields.")
    @GetMapping
    public List<OfferFilterResponseDto> getFilteredOffers(
            @Parameter(description = "City name", example = "Berlin")
            @RequestParam(required = false, defaultValue = "all") String cityName,

            @Parameter(description = "Category name", example = "Plumber")
            @RequestParam(required = false, defaultValue = "all") String category,

            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
            @RequestParam(required = false, defaultValue = "all") String keyPhrase
    ) {
        return service.getFilteredOffers(cityName, category, keyPhrase);
    }

}
