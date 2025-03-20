package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class OfferFilterController {

    private final OfferService service;

    public OfferFilterController(OfferService service) {
        this.service = service;
    }

    @GetMapping
    public List<OfferFilterResponseDto> getFilteredOffers(
            @RequestParam(required = false, defaultValue = "all") String cityName,
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam(required = false, defaultValue = "all") String keyPhrase
    ) {
        return service.getFilteredOffers(cityName, category, keyPhrase);
    }

}
