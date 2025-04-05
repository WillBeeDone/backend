package de.willbeedone.backend.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.willbeedone.backend.domain.entity.ImageGallery;
import de.willbeedone.backend.domain.entity.Offer;
import de.willbeedone.backend.exceptions.custom_exceptions.ImageUploadException;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.repository.OfferRepository;
import de.willbeedone.backend.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private final AmazonS3 client;
    private final OfferRepository repository;
    private final OfferServiceImpl offerService;

    @Autowired
    @Lazy
    private ImageServiceImpl self;

    public ImageServiceImpl(AmazonS3 client, OfferRepository repository, OfferServiceImpl offerService) {
        this.client = client;
        this.repository = repository;
        this.offerService = offerService;
    }



        private String generateUniqueFileName(MultipartFile file) {
            // Генерация уникального имени файла
            String sourceFileName = file.getOriginalFilename();
            int dotIndex = sourceFileName.lastIndexOf(".");
            String filename = sourceFileName.substring(0, dotIndex);
            String extension = sourceFileName.substring(dotIndex);
            return String.format("%s-%s%s", filename, UUID.randomUUID(), extension);
        }

    @Override
    public String uploadImage(MultipartFile file,Long userId) {
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
            // Получаем URL загруженного файла
            String url = client.getUrl("will-bee-done", uniqueName).toString();

            // Получаем активное предложение по userId
            Offer offer = offerService.getActiveOfferEntityById(userId);
            if (offer == null) {
                throw new OfferNotFoundException(userId); // Если предложение не найдено, выбрасываем исключение
            }

            // Создаем новый объект ImageGallery
            ImageGallery newImage = new ImageGallery();
            newImage.setImageUrl(url); // Устанавливаем URL изображения
            newImage.setOffer(offer); // Привязываем к предложению

            // Добавляем новое изображение в список изображений предложения
            offer.getImages().add(newImage);

            // Сохраняем обновленное предложение в базе
            repository.save(offer);

            // Возвращаем URL изображения
            return url;
        } catch (AmazonServiceException | IOException e) {
            // Обработка ошибок загрузки (например, если S3 не доступен)
            throw new ImageUploadException((AmazonServiceException) e);
        }
    }
    }


