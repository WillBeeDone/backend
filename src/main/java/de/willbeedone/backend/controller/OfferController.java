package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferProfileGuestResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserService userService;

    public OfferController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
    }

    @Operation(summary = "Getting all active offers",
            description = "Returns al active offers for the gallery.")
    @GetMapping("/all")
    public List<OfferFilterResponseDto> getAllActiveOffers() {
        return offerService.getAllActiveOffers();
    }


    @Operation(summary = "Getting all pageable active offers",
            description = "Returns all pageable active offers for the gallery. Default size - 9.")
    @GetMapping
    public Page<OfferFilterResponseDto> getAllActiveOffers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,

            @RequestParam(required = false)
            @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s-]+$", message = "City name can only contain letters, spaces, and hyphens")
            String cityName
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (cityName != null && !cityName.equals("all")) {
            return offerService.getActiveOffersByCity(cityName, pageable);
        }
        return offerService.getAllActiveOffers(pageable);
    }

    @Operation(summary = "Getting offer by id",
            description = "Returns precise offer by id for its profile card.")
    @GetMapping("/{id}")
    public Optional<OfferProfileGuestResponseDto> getActiveOfferByIdGuest(
            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long id) {
        return offerService.getActiveOfferById(id);
    }
    
    @Operation(summary = "Getting filtered offers",
            description = "Returns pageable active offers filtered by Category, Location or Key phrase from searching field. Filtration can include all, part or none of these fields.")
    @GetMapping("/filter")
    public Page<OfferFilterResponseDto> getFilteredOffers(
            @Parameter(description = "City name", example = "Berlin")
            @RequestParam(required = false, defaultValue = "all") String cityName,

            @Parameter(description = "Category name", example = "Plumber")
            @RequestParam(required = false, defaultValue = "all") String category,

            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
            @RequestParam(required = false, defaultValue = "all") String keyPhrase,

            @RequestParam(required = false) @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal minPrice,

            @RequestParam(required = false) @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal maxPrice,

            @RequestParam(required = false, defaultValue = "9") int size,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "pricePerHour,asc") String sort  // Новый параметр сортировки
    ) {

        String[] sortParams = sort.split(",");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        PageRequest pageRequest = PageRequest.of(page, size, sortOrder);

        return offerService.getFilteredOffers(cityName, category, keyPhrase, minPrice, maxPrice, pageRequest);
    }

    @Operation(summary = "Delete an offer by id",
            description = "Deletes an offer by its unique identifier.")
    @DeleteMapping("/{id}")
    public void deleteOfferById(@PathVariable Long id) {
        offerService.deleteOfferById(id);
    }

    @PostMapping("/user/{userId}/add-offer")
    public ResponseEntity<Offer> addOfferToUser(@PathVariable Long userId, @RequestBody OfferRequestDto request) {
        Offer createdOffer = offerService.addOfferToUser(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @GetMapping("/user/{userId}/offers")
    public ResponseEntity<List<Offer>> getUserOffers(@PathVariable Long userId) {
        List<Offer> offers = userService.getUserOffers(userId);
        return ResponseEntity.ok(offers);
    }

}
