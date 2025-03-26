package de.willbeedone.backend.exceptions.custom_exceptions;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String token) {
        super(String.format("Token - %s. is not valid", token));
    }
}
