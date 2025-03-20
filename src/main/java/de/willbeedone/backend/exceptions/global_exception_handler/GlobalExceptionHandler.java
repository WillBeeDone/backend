package de.willbeedone.backend.exceptions.global_exception_handler;

import de.willbeedone.backend.exceptions.custom_exceptions.AlreadyExistException;
import de.willbeedone.backend.exceptions.custom_exceptions.OfferNotFoundException;
import de.willbeedone.backend.exceptions.Response;
import de.willbeedone.backend.exceptions.custom_exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
