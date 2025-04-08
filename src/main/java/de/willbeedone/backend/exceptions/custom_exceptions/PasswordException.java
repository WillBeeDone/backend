package de.willbeedone.backend.exceptions.custom_exceptions;

public class PasswordException extends RuntimeException {
  public PasswordException(String message) {
    super(message);
  }
}
