package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.ImageGallery;
import de.willbeedone.backend.domain.entity.Offer;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile file);

    ImageGallery mapFileToImageGalleryDto(MultipartFile file, Offer offer);

}
