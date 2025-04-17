package de.willbeedone.backend.controller;

import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.AdminService;
import de.willbeedone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin controller", description = "Controller for various administrative operations.")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }

    @Operation(summary = "User Blocking by Administrator",
            description = "The user's binary parameter 'blocked' toggles its value to the opposite.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/block")
    public ResponseEntity<Response> blockUserByEmail(
            @Parameter(description = "User email", example = "offender@gmail.com")
            @RequestBody String email
    ) {
        email = email.trim().replaceAll("\"", "").replaceAll("'", "");

        boolean isBlocked = adminService.blockUserByEmail(email);

        String message = String.format("User with email %s has been %s.",
                email,
                isBlocked ? "blocked" : "unblocked"
        );

        return ResponseEntity.ok(new Response(message));
    }
}
