package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserEmailRequestDto;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin controller", description = "Controller for various administrative operations.")
public class AdminController {

    @Autowired
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User Blocking by Administrator",
            description = "The user's binary parameter 'blocked' toggles its value to the opposite.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/block")
    public Response blockUserByEmail(
            @Parameter(description = "User email", example = "offender@gmail.com")
            @RequestBody UserEmailRequestDto userEmailRequestDto
    ) {

        String email = userEmailRequestDto.getEmail();
        boolean isBlocked = userService.blockUserByEmail(email);

        String message = isBlocked ?
                "User " + email + " is now blocked." :
                "User " + email + " has been unblocked.";

        return new Response (message);
    }
}
