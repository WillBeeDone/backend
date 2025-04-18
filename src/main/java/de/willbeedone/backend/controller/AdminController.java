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

    private final UserService userService;
    private final AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
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

        String message = isBlocked ?
                "User " + email + " is now blocked." :
                "User " + email + " has been unblocked.";

        return ResponseEntity.ok(new Response(message));
    }

    @Operation(summary = "Toggle status of any user (admin only)",
            description = "Allows admin to toggle the 'active' status of any user by email.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/status")
    public ResponseEntity<Response> toggleAnyUserStatus(
            @Parameter(description = "User email", example = "offender@gmail.com")
            @RequestBody String email) {
        email = email.trim().replaceAll("\"", "").replaceAll("'", "");
        boolean wasActive = userService.getUserStatus(email);
        userService.toggleActiveStatus(email);
        boolean isActive = userService.getUserStatus(email);

        String message = isActive ?
                "User " + email + " is now active." :
                "User " + email + " has been deactivated.";

        return ResponseEntity.ok(new Response(message));
    }
}
