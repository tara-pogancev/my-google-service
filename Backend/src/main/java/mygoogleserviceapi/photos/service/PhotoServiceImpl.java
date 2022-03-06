package mygoogleserviceapi.photos.service;

import lombok.AllArgsConstructor;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.repository.PhotoRepository;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import mygoogleserviceapi.photos.service.interfaces.PhotoStorageService;
import mygoogleserviceapi.photos.validator.PhotoValidator;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoStorageService photoStorageService;
    private final PhotoRepository photoRepository;
    private final ApplicationUserService userService;
    private final PhotoValidator photoValidator;

    @Override
    public Photo savePhoto(MultipartFile file, String email) {
        if (!photoValidator.isValid(file)) {
            return null;
        }

        try {
            photoStorageService.savePhoto(file, email);
        } catch (RuntimeException e) {
            return null;
        }
        ApplicationUser user = userService.findByEmail(email);
        Photo photo = new Photo(file.getOriginalFilename(), user);
        return photoRepository.save(photo);
    }
}
