package mygoogleserviceapi.photos.service;

import lombok.AllArgsConstructor;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import mygoogleserviceapi.photos.repository.PhotoRepository;
import mygoogleserviceapi.photos.service.interfaces.ExifParser;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import mygoogleserviceapi.photos.service.interfaces.PhotoStorageService;
import mygoogleserviceapi.photos.validator.PhotoValidator;
import mygoogleserviceapi.shared.exception.EntityNotFoundException;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.AuthorizationService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final PhotoStorageService photoStorageService;
    private final PhotoRepository photoRepository;
    private final ApplicationUserService userService;
    private final PhotoValidator photoValidator;
    private final AuthorizationService authorizationService;
    private final ExifParser exifParser;

    private static final int PAGE_SIZE = 200;
    private static final long STORAGE_CAPACITY = 104_857_600L;

    @Override
    public Photo savePhoto(MultipartFile file, String email) throws IOException {
        authorizationService.isUserAllowedToAccessResource(email);
        if (!photoValidator.isValid(file)) {
            return null;
        }
        ApplicationUser user = userService.findByEmail(email);
        if (user == null)
            throw new EntityNotFoundException(ApplicationUser.class.getSimpleName());
        if (addingExceedsStorageCapacity(user.getId(), file))
            return null;
        try {
            photoStorageService.savePhoto(file, email);
            photoStorageService.savePhotoThumbnail(file, email);
        } catch (RuntimeException e) {
            return null;
        }
        Photo photo = photoRepository.getPhotoForUser(email, file.getOriginalFilename());
        if (photo == null) {
            LocalDateTime creationDate = photoStorageService.getCreationDate(file.getOriginalFilename(), email);
            photo = new Photo(file.getOriginalFilename(), user, creationDate, file.getSize());
        }
        PhotoMetadata metadata = getMetadata(photo);
        photo.setLatitude(metadata.getLatitude());
        photo.setLongitude(metadata.getLongitude());
        return photoRepository.save(photo);
    }


    @Override
    public List<Photo> getPhotosForUser(Long userId, Integer page) {
        authorizationService.isUserAllowedToAccessResource(userId);
        int pageNum = (page == null || page <= 0) ? 0 : page;
        Pageable pageRequest = PageRequest.of(pageNum, PAGE_SIZE);
        return photoRepository.getPhotosForUserId(userId, pageRequest).toList();
    }

    @Override
    public Resource getPhotoFile(String filename) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        return photoStorageService.getPhoto(filename, email);
    }

    @Override
    public Resource getPhotoThumbnailFile(String filename) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        return photoStorageService.getPhotoThumbnail(filename, email);
    }

    @Override
    public void deletePhoto(String filename) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        photoStorageService.deletePhoto(filename, email);
        photoStorageService.deleteThumbnail(filename, email);
        photoRepository.deleteById(photo.getId());
    }

    @Override
    public void deleteAllPhotos(String email) {
        this.photoStorageService.deleteAllPhotos(email);
    }

    @Override
    public PhotoMetadata getMetadata(Photo photo) throws IOException {
        return photoStorageService.getMetadata(photo);
    }

    @Override
    public void rotatePhoto(String filename) throws IOException {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        File photoFile = photoStorageService.getPhoto(filename, email).getFile();
        exifParser.rotate(photoFile);
        File photoThumbnailFile = photoStorageService.getPhotoThumbnail(filename, email).getFile();
        exifParser.rotate(photoThumbnailFile);
    }

    @Override
    public void updateMetadata(String filename, PhotoMetadata metadata) throws IOException {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        photo.setLatitude(metadata.getLatitude());
        photo.setLongitude(metadata.getLongitude());
        photoRepository.save(photo);
        photoStorageService.setMetadata(photo, metadata);
    }

    @Override
    public void favoritePhoto(String filename, boolean favorite) {
        String email = authorizationService.getUsername();
        Photo photo = getPhotoForUserOrThrowNotFound(email, filename);
        photo.setFavorite(favorite);
        photoRepository.save(photo);
    }

    @Override
    public List<File> getPhotoFilesForExport(Long userId, List<String> fileNames) {
        Set<Photo> photos = photoRepository.getPhotosForExport(userId, fileNames);
        return photos.stream().map(photo -> {
            try {
                return getPhotoFile(photo.getFileName()).getFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Long getUsedStorage(Long userId) {
        authorizationService.isUserAllowedToAccessResource(userId);
        Long res = photoRepository.getUsedStorageForUserID(userId);
        if (res == null)
            return 0L;
        return res;
    }

    @Override
    public Long getStorageCapacity() {
        return STORAGE_CAPACITY;
    }


    private boolean addingExceedsStorageCapacity(Long userid, MultipartFile photo) {
        return getUsedStorage(userid) + photo.getSize() > getStorageCapacity();
    }


    private Photo getPhotoForUserOrThrowNotFound(String email, String fileName) {
        Photo photo = photoRepository.getPhotoForUser(email, fileName);
        if (photo == null)
            throw new EntityNotFoundException(Photo.class.getSimpleName());
        return photo;
    }
}
