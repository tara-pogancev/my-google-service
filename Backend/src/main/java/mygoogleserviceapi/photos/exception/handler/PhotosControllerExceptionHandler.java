package mygoogleserviceapi.photos.exception.handler;

import mygoogleserviceapi.photos.exception.ImageMetadataIOException;
import mygoogleserviceapi.shared.exception.handler.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class PhotosControllerExceptionHandler {
    @ExceptionHandler(ImageMetadataIOException.class)
    public ResponseEntity<ErrorMessage> imageMetadataIOException(ImageMetadataIOException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage(),
                new Date());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
