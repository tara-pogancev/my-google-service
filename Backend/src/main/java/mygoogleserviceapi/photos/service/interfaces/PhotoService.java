package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    Photo savePhoto(MultipartFile file, String email);

    List<Photo> getPhotosForUser(Long userId, Integer page);

    public Resource getPhotoFile(String filename);

    void deletePhoto(String filename);

    void deleteAllPhotos(String email);

    PhotoMetadata getMetadata(Photo photo);

    void rotatePhoto(String filename);

    void updateMetadata(String filename, PhotoMetadata metadata);
}
