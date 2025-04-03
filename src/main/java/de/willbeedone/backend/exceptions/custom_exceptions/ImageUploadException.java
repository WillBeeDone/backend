package de.willbeedone.backend.exceptions.custom_exceptions;

import com.amazonaws.AmazonServiceException;

import java.io.IOException;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException(AmazonServiceException e) {
        super(String.format("Error uploading image "));
    }

    public ImageUploadException(OfferNotFoundException e) {
        super(String.format("Offer not found"));
    }

    public ImageUploadException(IOException e) {
        super(String.format("Error processing the uploaded file"));
    }

}
