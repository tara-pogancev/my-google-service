package mygoogleserviceapi.photos.exception;

public class ImageMetadataIOException extends RuntimeException {
    public ImageMetadataIOException() {
        super("Unable to read or write to given image");
    }
}
