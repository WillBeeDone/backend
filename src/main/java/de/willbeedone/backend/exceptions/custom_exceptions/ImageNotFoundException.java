package de.willbeedone.backend.exceptions.custom_exceptions;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
    }
    public ImageNotFoundException(Long id) {
        super(String.format("Image with id: %d not found", id));
    }
}
