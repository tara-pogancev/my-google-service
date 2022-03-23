package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoStorageService {

    MultipartFile savePhoto(MultipartFile file, String email);

    Resource getPhoto(String fileName, String email);

    File getPhotoFile(String fileName, String email);

    PhotoMetadata getMetadata(Photo photo);

    void deletePhoto(String fileName, String email);

    void deleteAllPhotos(String email);

    void setMetadata(Photo photo, PhotoMetadata metadata);
}
