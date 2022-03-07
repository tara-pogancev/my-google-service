package mygoogleserviceapi.photos.validator;

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
        return isSupportedContentType(file.getContentType());
    }


    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
