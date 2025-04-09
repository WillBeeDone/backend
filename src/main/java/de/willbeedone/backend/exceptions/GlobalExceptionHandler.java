package de.willbeedone.backend.exceptions;

import de.willbeedone.backend.exceptions.custom_exceptions.*;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.OfferValidationException;
import de.willbeedone.backend.exceptions.custom_validation_exceptions.UserValidationException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(UserNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<Response> handleException(OfferNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Response> handleException(AlreadyExistException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<Response> handleException(UserValidationException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferValidationException.class)
    public ResponseEntity<Response> handleException(OfferValidationException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConfirmationCodeIsInvalidException.class)
    public ResponseEntity<Response> handleException(ConfirmationCodeIsInvalidException e) {
        Response response = new Response(e.getMessage());
        if (e.getMessage().contains("found")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Response> handleException(AuthException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleException(DataIntegrityViolationException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body("Invalid parameter: " + e.getMessage());
    }

    @ExceptionHandler(OfferNotBelongToUserException.class)
    public ResponseEntity<Response> handleException(OfferNotBelongToUserException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Response> handleException(PasswordException e) {
        Response response = new Response(e.getMessage());
        if (e.getMessage().contains("mail")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (e.getMessage().contains("old")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


}
