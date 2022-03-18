package mygoogleserviceapi.photos.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoStorageService {

    MultipartFile savePhoto(MultipartFile file, String email);

    Resource getPhoto(String fileName, String email);

    File getPhotoFile(String fileName, String email);

    void deletePhoto(String fileName, String email);

    void deleteAllPhotos(String email);
}
