package de.willbeedone.backend.controller;

import de.willbeedone.backend.domain.dto.user_dto.request_dto.UserRequestDto;
import de.willbeedone.backend.domain.entity.User;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.service.interfaces.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long register(@RequestBody UserRequestDto inboundUser) {
       return  userService.register(inboundUser);
    }

    @GetMapping("/{code}")
    public Response confirmRegistration(@PathVariable String code) {
        userService.confirmRegistration(code);
        return new Response("Registration successfully confirmed.");
    }
}
