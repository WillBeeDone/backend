package de.willbeedone.backend.exceptions.custom_exceptions;

public class ConfirmationCodeIsInvalidException extends RuntimeException {
    public ConfirmationCodeIsInvalidException(String message) {
        super(message);
    }
}
