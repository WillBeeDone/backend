package de.willbeedone.backend.controller;

import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.service.interfaces.ImageGalleryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class ImagesController {

    private final ImageGalleryService imageGalleryService;


    public ImagesController(ImageGalleryService imageGalleryService) {
        this.imageGalleryService = imageGalleryService;
    }

    @PostMapping
    public Response upload(
            @RequestParam MultipartFile file,
            @RequestParam Long offerId
    ) {
        String url = imageGalleryService.upload(file, offerId);
        return new Response("Saved image URL - " + url);
    }
}
