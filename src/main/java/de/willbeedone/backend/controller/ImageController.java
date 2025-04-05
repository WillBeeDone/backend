package de.willbeedone.backend.controller;

import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.security.sec_service.TokenService;
import de.willbeedone.backend.service.interfaces.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    private final TokenService tokenService;

    public ImageController(ImageService imageService, TokenService tokenService) {
        this.imageService = imageService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public Response upload(
            @RequestParam MultipartFile file,
            @RequestHeader("Authorization") String token
    ) {
        String email = tokenService.extractEmailFromToken(token);
        String url = imageService.uploadImage(file);
        return new Response("Saved image URL - " + url);
    }
}
