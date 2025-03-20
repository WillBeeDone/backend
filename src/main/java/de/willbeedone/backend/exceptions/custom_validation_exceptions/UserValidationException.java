package de.willbeedone.backend.exceptions.custom_validation_exceptions;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(Throwable cause) {
        super(cause);
    }
}
