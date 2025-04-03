package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users.")
public class UserController {

    @Autowired
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Get all pageable favourite active offers",
            description = "Returns all pageable favourite active offers for user by his id. Returns filtrated if it's needed. Default size - 9.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/favourites")
    public Page<OfferFilterResponseDto> getAllFavouriteOffers(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Starting page number", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of elements on the page", example = "9")
            @RequestParam(defaultValue = "9") int size,

            @Parameter(description = "City name", example = "Berlin")
            @RequestParam(required = false, defaultValue = "all") String cityName,

            @Parameter(description = "Category name", example = "Plumber")
            @RequestParam(required = false, defaultValue = "all") String category,

            @Parameter(description = "Key phrase from searching field", example = "Plumber with beard")
            @RequestParam(required = false, defaultValue = "all") String keyPhrase
    ) {
        String email = tokenService.extractEmailFromToken(token);

        Pageable pageable = PageRequest.of(page, size);

        if (!"all".equals(cityName) || !"all".equals(category) || !"all".equals(keyPhrase)
        ) {
            return userService.getAllFavouriteFilteredOffers(email, pageable, cityName, category, keyPhrase);
        }

        return userService.getAllFavouriteOffers(email, pageable);
    }

    @Operation(summary = "Add offer to favourites",
            description = "Adds offer to user's favourites. Finds exact offer by its id.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/favourites/{offerId}")
    public Response addOfferToFavourite(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long offerId
    ) {
        String email = tokenService.extractEmailFromToken(token);
        userService.addOfferToFavourite(email, offerId);
        return new Response("OK");
    }

    @Operation(summary = "Delete offer from favourites",
            description = "Deletes offer from user's favourites. Finds exact offer and user by their id.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/favourites/{offerId}")
    public Response deleteOfferFromFavourite(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long offerId
    ) {
        String email = tokenService.extractEmailFromToken(token);
        userService.removeOfferFromFavourite(email, offerId);
        return new Response("OK");
    }

    @Operation(summary = "Clear favourites",
            description = "Deletes all offers from user's favourites. Finds exact user by id.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/favourites")
    public Response clearAllFromFavourite(
            @RequestHeader("Authorization") String token
    ) {
        String email = tokenService.extractEmailFromToken(token);
        userService.removeAllOffersFromFavourite(email);
        return new Response("OK");
    }

}
