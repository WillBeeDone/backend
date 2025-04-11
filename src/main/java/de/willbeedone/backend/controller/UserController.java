package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.offer_dto.request_dto.OfferRequestDto;
import de.willbeedone.backend.domain.dto.offer_dto.response_dto.OfferFilterResponseDto;
import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserForOfferRequestDto;
import de.willbeedone.backend.domain.dto.user_dto.response_dto.UserProfileResponseDto;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.OfferService;
import de.willbeedone.backend.service.interfaces.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Controller for various operations with Users.")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final OfferService offerService;
    @Autowired
    private final TokenService tokenService;

    public UserController(UserService userService, OfferService offerService, TokenService tokenService) {
        this.userService = userService;
        this.offerService = offerService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Get all pageable favourite active offers",
            description = "Returns all pageable favourite active offers for user by his id. Returns filtrated if it's needed. Default size - 12.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/favourites")
    public Page<OfferFilterResponseDto> getAllFavouriteOffers(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Starting page number", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of elements on the page", example = "9")
            @RequestParam(defaultValue = "12") int size,

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

    @Operation(summary = "Show user's profile",
            description = "Shows user's profile.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public UserProfileResponseDto getUserProfile(
            @RequestHeader("Authorization") String token
    ) {
        String email = tokenService.extractEmailFromToken(token);
        return userService.getUserProfile(email);
    }

    @Operation(summary = "Update user's profile",
            description = "Updates user's profile, filling all missing fields.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response updateUserProfileForOffer(
            @RequestHeader("Authorization") String token,
            @ModelAttribute UserForOfferRequestDto userDto
    ) {
        String email = tokenService.extractEmailFromToken(token);
        userService.updateUser(userDto, email);
        return new Response("OK");
    }

    @Operation(summary = "Add new offer",
            description = "Adds new user's offer.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(
            value = "/offers",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response addNewOffer(
            @RequestHeader("Authorization") String token,
            @ModelAttribute OfferRequestDto offerRequestDto
    ) {
        String email = tokenService.extractEmailFromToken(token);
        offerService.addNewOffer(offerRequestDto, email);
        return new Response("OK");
    }

    @Operation(summary = "Update offer",
            description = "Updates user's offer.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(
            value = "/offers/{offerId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Response updateOffer(
            @RequestHeader("Authorization") String token,
            @ModelAttribute OfferRequestDto offerRequestDto,
            @PathVariable Long offerId
    ) {
        String email = tokenService.extractEmailFromToken(token);
        offerService.updateOffer(offerRequestDto, offerId, email);
        return new Response("OK");
    }

    @Operation(summary = "Activate/Deactivate offer",
            description = "Activates or Deactivate user's offer by its id.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/offers/{offerId}")
    public Response changeOfferActivation(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long offerId
    ) {
        String email = tokenService.extractEmailFromToken(token);
        Offer offer = offerService.getOfferEntityById(offerId);
        if (!offer.isActive()) {
            offerService.activateOfferById(email, offer);
            return new Response("OK");
        }
        offerService.deactivateOfferById(email, offer);
        return new Response("OK");
    }

    @Operation(summary = "Delete offer",
            description = "Delete user's offer by its id.")
    @PreAuthorize(" hasAuthority('ROLE_USER')")
    @DeleteMapping("/offers/{offerId}")
    public Response deleteOfferById(
            @RequestHeader("Authorization") String token,

            @Parameter(description = "Offer unique identifier", example = "1")
            @PathVariable Long offerId
    ) {
        String email = tokenService.extractEmailFromToken(token);
        offerService.deleteOfferById(email, offerId);
        return new Response("OK");
    }

    @Operation(summary = "Show user offers",
            description = "Show user offers for page MyOffers.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/offers")
    public List<OfferFilterResponseDto> getMyOffers(@RequestHeader("Authorization") String token) {
        String email = tokenService.extractEmailFromToken(token);

        return userService.getOffersByUserId(email);
    }

}
