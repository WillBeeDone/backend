package de.willbeedone.backend.exceptions.custom_exceptions;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String email) {

        super(String.format("User with email: %s already exist", email));
    }
}
