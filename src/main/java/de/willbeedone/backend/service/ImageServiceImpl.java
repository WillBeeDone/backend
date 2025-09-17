package de.willbeedone.backend.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.willbeedone.backend.domain.dto.image_gallery_dto.ImageGalleryDto;
import de.willbeedone.backend.domain.entity.ImageGallery;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.ImageUploadException;
import de.willbeedone.backend.service.interfaces.ImageService;
import de.willbeedone.backend.service.mapping.ImageGalleryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final AmazonS3 client;
    private final ImageGalleryMappingService imageGalleryMappingService;

    @Autowired
    public ImageServiceImpl(Optional<AmazonS3> clientOpt, ImageGalleryMappingService imageGalleryMappingService) {
        this.client = clientOpt.orElse(null);
        this.imageGalleryMappingService = imageGalleryMappingService;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        if (client == null) {
            throw new ImageUploadException(new AmazonServiceException("S3 client not configured"));
        }

        try {
            // Генерация уникального имени файла
            String uniqueName = generateUniqueFileName(file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            // Подготовка запроса для загрузки файла в S3
            PutObjectRequest request = new PutObjectRequest(
                    "will-bee-done", uniqueName, file.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            // Загружаем файл в S3
            client.putObject(request);

            // Возвращаем URL изображения
            return client.getUrl("will-bee-done", uniqueName).toString();
        } catch (AmazonServiceException | IOException e) {
            throw new ImageUploadException((AmazonServiceException) e);
        }
    }

    @Override
    public ImageGallery mapFileToImageGalleryDto(MultipartFile file, Offer offer) {
        String imageUrl = uploadImage(file);
        ImageGalleryDto dto = new ImageGalleryDto();
        dto.setImageUrl(imageUrl);
        dto.setOffer(offer);
        return imageGalleryMappingService.mapRequestDtoToEntity(dto);
    }

    private String generateUniqueFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf(".");
        String filename = sourceFileName.substring(0, dotIndex);
        String extension = sourceFileName.substring(dotIndex);
        return String.format("%s-%s%s", filename, UUID.randomUUID(), extension);
    }
}

