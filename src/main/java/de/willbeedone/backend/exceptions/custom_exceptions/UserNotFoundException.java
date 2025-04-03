package de.willbeedone.backend.exceptions.custom_exceptions;
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String param) {
        super(String.format("User with characteristic: %s not found", param));
    }
    public UserNotFoundException(Long id) {
        super(String.format("User with id: %d not found", id));
    }
    public UserNotFoundException() {
        super("No users found");
    }
}
