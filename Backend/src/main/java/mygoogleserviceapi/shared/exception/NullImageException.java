package mygoogleserviceapi.shared.exception;

public class NullImageException extends RuntimeException {
    public NullImageException() {
        super("Please select a file to be uploaded.");
    }
}
