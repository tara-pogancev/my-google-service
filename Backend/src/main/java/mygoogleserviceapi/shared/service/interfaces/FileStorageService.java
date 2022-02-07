package mygoogleserviceapi.shared.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    public String storeProfilePicture(MultipartFile file, Long userId);

    public Resource loadProfilePicture(Long userId);

}
