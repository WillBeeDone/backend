package de.willbeedone.backend.exceptions.custom_exceptions;
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(String.format("User with email: %s not found", email));
    }
    public UserNotFoundException(Long id) {
        super(String.format("User with id: %d not found", id));
    }
    public UserNotFoundException() {
        super("No users found");
    }
}
