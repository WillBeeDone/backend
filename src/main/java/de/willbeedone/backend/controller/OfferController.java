package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.PagedDataDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.mapping.OfferMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offers")
@Tag(name = "Offer controller", description = "Controller for various operations with Offers")
public class OfferController {

    @Autowired
    private final OfferService service;
    private final OfferMappingService mappingService;

    public OfferController(OfferService service, OfferMappingService mappingService) {
        this.service = service;
        this.mappingService = mappingService;
    }

    @Operation(summary = "Getting all active offers",
            description = "Returns al active offers for the gallery.")
    @GetMapping("/all")
    public List<OfferFilterResponseDto> getAllActiveOffers() {
        return service.getAllActiveOffers();
    }


    @Operation(summary = "Getting all pageable active offers",
            description = "Returns all pageable active offers for the gallery. Default size - 9")
    @GetMapping
    public Page<OfferFilterResponseDto> getAllActiveOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllActiveOffers(pageable);
    }

    @Operation(summary = "Getting offer by id",
            description = "Returns precise offer by id for its profile card.")
    @GetMapping("/{id}")
    public OfferProfileGuestResponseDto getActiveOfferByIdGuest(
            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long id) {
        return service.getActiveOfferById(id);
    }
    @Operation(summary = "Getting filtered offers",
            description = "Returns offers filtered by Category, Location or Key phrase from searching field. Filtration can include all, part or none of these fields")
    @GetMapping("/filter")
    public PagedDataDto<OfferFilterResponseDto> getFilteredOffers(
            @Parameter(description = "City name", example = "Berlin")
            @RequestParam(required = false, defaultValue = "all") String cityName,

            @Parameter(description = "Category name", example = "Plumber")
            @RequestParam(required = false, defaultValue = "all") String category,

            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
            @RequestParam(required = false, defaultValue = "all") String keyPhrase,

            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "9") int size
    ) {
        Page<OfferFilterResponseDto> pagedData = service.getFilteredOffers(cityName, category, keyPhrase, PageRequest.of(page, size));

        PagedDataDto<OfferFilterResponseDto> pagedDataDto = new PagedDataDto<>();
        pagedDataDto.setData(pagedData.getContent());
        pagedDataDto.setTotal(pagedData.getTotalElements());


        return pagedDataDto;
    }


//    @Operation(summary = "Getting filtered offers",
//            description = "Returns offers filtered by Category, Location or Key phrase from searching field. Filtration can include all, part or none of thees fields")
//    @GetMapping("/filter")
//    public List<OfferFilterResponseDto> getFilteredOffers(
//            @Parameter(description = "City name", example = "Berlin")
//            @RequestParam(required = false, defaultValue = "all") String cityName,
//
//            @Parameter(description = "Category name", example = "Plumber")
//            @RequestParam(required = false, defaultValue = "all") String category,
//
//            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
//            @RequestParam(required = false, defaultValue = "all") String keyPhrase
//    ) {
//        return service.getFilteredOffers(cityName, category, keyPhrase);
//    }


}
