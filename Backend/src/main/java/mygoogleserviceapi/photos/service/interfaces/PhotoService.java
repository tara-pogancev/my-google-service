package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {

    Photo savePhoto(MultipartFile file, String email);

}
