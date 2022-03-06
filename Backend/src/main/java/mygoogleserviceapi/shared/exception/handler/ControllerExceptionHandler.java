package mygoogleserviceapi.shared.exception.handler;

import mygoogleserviceapi.shared.exception.InvalidImageDimensionException;
import mygoogleserviceapi.shared.exception.InvalidImageFormatException;
import mygoogleserviceapi.shared.exception.NotAllowedException;
import mygoogleserviceapi.shared.exception.NullImageException;
import mygoogleserviceapi.shared.exception.ProfilePictureNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(InvalidImageFormatException.class)
    public ResponseEntity<ErrorMessage> invalidImageFormatException(InvalidImageFormatException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidImageDimensionException.class)
    public ResponseEntity<ErrorMessage> invalidImageDimensionException(InvalidImageDimensionException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullImageException.class)
    public ResponseEntity<ErrorMessage> nullImageException(NullImageException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfilePictureNotFoundException.class)
    public ResponseEntity<ErrorMessage> profilePictureNotFoundException(ProfilePictureNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorMessage> notAllowedException(NotAllowedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
