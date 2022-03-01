package mygoogleserviceapi.contacts.validator;

import mygoogleserviceapi.contacts.validator.annotation.ValidContactPicture;
import mygoogleserviceapi.shared.exception.InvalidImageFormatException;
import mygoogleserviceapi.shared.exception.NullImageException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ContactPictureValidator implements ConstraintValidator<ValidContactPicture, MultipartFile> {

    @Override
    public void initialize(ValidContactPicture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
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
        } catch (IOException e) {
            return false;
        }
        //TODO: add check for size
        return true;
    }


    private boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }

}
