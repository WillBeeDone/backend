package de.willbeedone.backend.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile file, Long imageId);

}
