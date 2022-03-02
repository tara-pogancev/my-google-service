package mygoogleserviceapi.photos.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoStorageService {

    MultipartFile savePhoto(MultipartFile file, String email);
}
