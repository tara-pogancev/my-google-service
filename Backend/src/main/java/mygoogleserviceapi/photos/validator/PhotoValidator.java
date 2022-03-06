package mygoogleserviceapi.photos.validator;

import mygoogleserviceapi.shared.exception.InvalidImageFormatException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoValidator {
    public PhotoValidator() {
    }

    public boolean isValid(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            return false;
        }
        if (!isSupportedContentType(file.getContentType())) {
            throw new InvalidImageFormatException();
        }
        return true;
    }


    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
