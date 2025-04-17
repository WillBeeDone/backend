package de.willbeedone.backend.controller;

import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin controller", description = "Controller for various administrative operations.")
public class AdminController {

    private final TokenService tokenService;
    private final AdminService adminService;

    public AdminController(TokenService tokenService, AdminService adminService) {
        this.tokenService = tokenService;
        this.adminService = adminService;
    }

    @Operation(
            summary = "Get all active favorite offers with pagination",
            description = "Returns a paginated list of all active favorite offers for the user by their ID. Applies filters if provided. Default page size is 12."
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/block")
    public Response blockUserByEmail(
            @RequestHeader("Authorization") String token,
            @Parameter(description = "User email", example = "offender@gmail.com")
            @RequestBody String email

    ) {
        adminService.blockUserByEmail(email);
        return new Response("Ok");
    }
}

