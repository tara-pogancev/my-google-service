package mygoogleserviceapi.shared.exception;

public class NotAllowedException extends RuntimeException {
    public NotAllowedException() {
        super("User is not allowed to do this.");
    }
}
