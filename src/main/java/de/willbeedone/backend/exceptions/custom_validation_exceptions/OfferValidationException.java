package de.willbeedone.backend.exceptions.custom_validation_exceptions;

public class OfferValidationException extends RuntimeException {
    public OfferValidationException(String message) {
        super(message);
    }

    public OfferValidationException(Throwable cause) {
        super(cause);
    }
}
