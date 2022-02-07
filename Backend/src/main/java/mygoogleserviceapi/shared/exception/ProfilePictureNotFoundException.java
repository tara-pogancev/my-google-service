package mygoogleserviceapi.shared.exception;

public class ProfilePictureNotFoundException extends RuntimeException {
    public ProfilePictureNotFoundException() {
        super("Profile picture not found");
    }
}
