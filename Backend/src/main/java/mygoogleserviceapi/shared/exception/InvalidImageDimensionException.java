package mygoogleserviceapi.shared.exception;

public class InvalidImageDimensionException extends RuntimeException {
    public InvalidImageDimensionException() {
        super("The dimensions for the image must be the same.");
    }
}
