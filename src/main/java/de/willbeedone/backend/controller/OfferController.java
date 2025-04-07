package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.service.interfaces.OfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offers")
@Tag(name = "Offer controller", description = "Controller for various operations with Offers.")
public class OfferController {

    @Autowired
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @Operation(summary = "Getting all active offers",
            description = "Returns al active offers for the gallery.")
    @GetMapping("/all")
    public List<OfferFilterResponseDto> getAllActiveOffers() {
        return offerService.getAllActiveOffers();
    }

    @Operation(summary = "Getting offer by id",
            description = "Returns precise offer by id for its profile card.")
    @GetMapping("/{id}")
    public Optional<OfferProfileGuestResponseDto> getActiveOfferByIdGuest(
            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long id) {
        return offerService.getActiveOfferById(id);
    }
    
    @Operation(summary = "Getting filtered pageable offers",
            description = "Returns pageable offers filtered by Category, Location or Key phrase from searching field. Filtration can include all, part or none of these fields.")
    @GetMapping
    public Page<OfferFilterResponseDto> getFilteredOffers(
            @Parameter(description = "City name", example = "Berlin")
            @RequestParam(required = false, defaultValue = "all") String cityName,

            @Parameter(description = "Category name", example = "Plumber")
            @RequestParam(required = false, defaultValue = "all") String category,

            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
            @RequestParam(required = false, defaultValue = "all") String keyPhrase,

            @RequestParam(required = false) @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal minPrice,

            @RequestParam(required = false) @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal maxPrice,

            @RequestParam(required = false, defaultValue = "12") int size,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "pricePerHour,asc") String sort  // Новый параметр сортировки
    ) {

        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        PageRequest pageRequest = PageRequest.of(page, size, sortOrder);

        return offerService.getFilteredOffers(cityName, category, keyPhrase, minPrice, maxPrice, pageRequest);
    }

}
