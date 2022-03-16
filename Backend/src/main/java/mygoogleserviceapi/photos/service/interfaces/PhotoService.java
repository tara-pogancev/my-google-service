package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface PhotoService {

    Photo savePhoto(MultipartFile file, String email);

    Set<Photo> getPhotosForUser(Long userId, Integer page);

    public Resource getPhotoFile(String filename);

    void deletePhoto(String filename);

    void deleteAllPhotos(String email);
}
