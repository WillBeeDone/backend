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
import de.willbeedone.backend.service.interfaces.ImageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageGalleryServiceImpl implements ImageGalleryService {

    @Autowired
    private final AmazonS3 client;
    private final OfferRepository repository;
    private final OfferServiceImpl offerService;

    @Autowired
    @Lazy
    private ImageGalleryServiceImpl self;

    public ImageGalleryServiceImpl(AmazonS3 client, OfferRepository repository, OfferServiceImpl offerService) {
        this.client = client;
        this.repository = repository;
        this.offerService = offerService;
    }


    @Override
    public String upload(MultipartFile file, Long offerId) {
        try {
            String uniqueName = generateUniqueFileName(file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    "will-bee-done", uniqueName, file.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);
            client.putObject(request);
            String url = client.getUrl("will-bee-done", uniqueName).toString();

            Offer offer = offerService.getActiveOfferEntityById(offerId);
            if (offer == null) {
                throw new OfferNotFoundException(offerId);
            }

            ImageGallery newImage = new ImageGallery();
            newImage.setImageUrl(url);
            newImage.setOffer(offer);
            offer.getImages().add(newImage);
            repository.save(offer);
            return url;
            //TODO проверь bucketName пожалуйста
        } catch (AmazonServiceException | IOException e) {
            throw new ImageUploadException((AmazonServiceException) e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        int dotIndex = sourceFileName.lastIndexOf(".");
        String filename = sourceFileName.substring(0, dotIndex);
        String extension = sourceFileName.substring(dotIndex);
        return String.format("%s-%s%s", filename, UUID.randomUUID(), extension);
    }
}
