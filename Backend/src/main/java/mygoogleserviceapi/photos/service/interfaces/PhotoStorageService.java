package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

public interface PhotoStorageService {

    MultipartFile savePhoto(MultipartFile file, String email);

    void savePhotoThumbnail(MultipartFile file, String email);

    Resource getPhoto(String fileName, String email);


    Resource getPhotoThumbnail(String fileName, String email);

    PhotoMetadata getMetadata(Photo photo) throws IOException;

    void deletePhoto(String fileName, String email);

    void deleteThumbnail(String fileName, String email);

    void deleteAllPhotos(String email);

    void setMetadata(Photo photo, PhotoMetadata metadata) throws IOException;

    LocalDateTime getCreationDate(String fileName, String email) throws IOException;
}
