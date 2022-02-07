package mygoogleserviceapi.shared.validator;

import mygoogleserviceapi.shared.exception.InvalidImageDimensionException;
import mygoogleserviceapi.shared.exception.InvalidImageFormatException;
import mygoogleserviceapi.shared.exception.NullImageException;
import mygoogleserviceapi.shared.validator.annotation.ValidProfilePicture;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfilePictureValidator implements ConstraintValidator<ValidProfilePicture, MultipartFile> {
    @Override
    public void initialize(ValidProfilePicture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new NullImageException();
        }
        String contentType = multipartFile.getContentType();
        if (!isSupportedContentType(contentType)) {
            throw new InvalidImageFormatException();
        }
        byte[] image = new byte[0];
        try {
            image = multipartFile.getBytes();
            InputStream in = new ByteArrayInputStream(image);
            BufferedImage originalImage = null;
            originalImage = ImageIO.read(in);
            // Get image dimensions
            int height = originalImage.getHeight();
            int width = originalImage.getWidth();
            if (height != width) {
                throw new InvalidImageDimensionException();
            }
        } catch (IOException e) {
            return false;
        }
        //TODO: add check for size
        return result;
    }


    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
