package mygoogleserviceapi.photos.service;

import lombok.AllArgsConstructor;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.repository.PhotoRepository;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import mygoogleserviceapi.photos.service.interfaces.PhotoStorageService;
import mygoogleserviceapi.photos.validator.PhotoValidator;
import mygoogleserviceapi.shared.exception.EntityNotFoundException;
import mygoogleserviceapi.shared.exception.NotAllowedException;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.AuthorizationService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoStorageService photoStorageService;
    private final PhotoRepository photoRepository;
    private final ApplicationUserService userService;
    private final PhotoValidator photoValidator;
    private final AuthorizationService authorizationService;

    @Override
    public Photo savePhoto(MultipartFile file, String email) {
        if (!authorizationService.isEmailInJWT(email)) {
            throw new NotAllowedException();
        }
        if (!photoValidator.isValid(file)) {
            return null;
        }
        ApplicationUser user = userService.findByEmail(email);
        if (user == null)
            throw new EntityNotFoundException(ApplicationUser.class.getSimpleName());
        try {
            photoStorageService.savePhoto(file, email);
        } catch (RuntimeException e) {
            return null;
        }
        Photo photo = photoRepository.getPhotoForUser(email, file.getOriginalFilename());
        if (photo == null) {
            photo = new Photo(file.getOriginalFilename(), user);
        }
        return photoRepository.save(photo);
    }

    @Override
    public Set<Photo> getPhotosForUser(Long userId) {
        if (!authorizationService.isEmailInJWT(userId)) {
            throw new NotAllowedException();
        }
        return photoRepository.getPhotosForUserId(userId);
    }

    @Override
    public Resource getPhotoFile(String filename) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        return photoStorageService.getPhoto(filename, email);
    }

    @Override
    public void deletePhoto(String filename) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        photoStorageService.deletePhoto(filename, email);
        photoRepository.deleteById(photo.getId());
    }

    @Override
    public void deleteAllPhotos(String email) {
        this.photoStorageService.deleteAllPhotos(email);
    }

    private Photo getPhotoForUserOrThrowNotFound(String email, String fileName) {
        Photo photo = photoRepository.getPhotoForUser(email, fileName);
        if (photo == null)
            throw new EntityNotFoundException(Photo.class.getSimpleName());
        return photo;
    }
}
