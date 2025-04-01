package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@Tag(name = "Registration controller", description = "Controller for registration and registration confirmation.")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User registration",
            description = "Creates new user in database and sends confirmation e-mail to user.")
    @PostMapping
    public Long register(@RequestBody UserRequestDto inboundUser) {
       return userService.register(inboundUser);
    }

    @Operation(summary = "User's registration confirmation",
            description = "Sets user to active status (active = true).")
    @GetMapping("/{code}")
    public Long confirmRegistration(@PathVariable String code) {
        return userService.confirmRegistration(code);
    }

}
