package de.willbeedone.backend.controller;

import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.service.interfaces.ImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;


    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public Response upload(
            @RequestParam MultipartFile file,
            @RequestParam Long offerId
    ) {
        String url = imageService.upload(file, offerId);
        return new Response("Saved image URL - " + url);
    }
}
