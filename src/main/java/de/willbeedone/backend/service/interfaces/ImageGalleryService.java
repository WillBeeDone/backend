package de.willbeedone.backend.service.interfaces;

import de.willbeedone.backend.domain.entity.ImageGallery;
import org.springframework.web.multipart.MultipartFile;

public interface ImageGalleryService {
    String upload(MultipartFile file, Long userId);

}
