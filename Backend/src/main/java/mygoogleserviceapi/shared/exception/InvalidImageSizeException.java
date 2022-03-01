package mygoogleserviceapi.shared.exception;

public class InvalidImageSizeException extends RuntimeException {
    public InvalidImageSizeException() {
        super("Max image size is 2MB.");
    }
}
