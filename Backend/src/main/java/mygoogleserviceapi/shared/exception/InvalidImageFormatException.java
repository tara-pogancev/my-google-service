package mygoogleserviceapi.shared.exception;

public class InvalidImageFormatException extends RuntimeException {
    public InvalidImageFormatException() {
        super("Only PNG or JPG images are allowed.");
    }
}
