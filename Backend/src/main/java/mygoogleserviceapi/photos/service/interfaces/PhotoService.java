package mygoogleserviceapi.photos.service.interfaces;

import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface PhotoService {

    Photo savePhoto(MultipartFile file, String email) throws IOException;

    List<Photo> getPhotosForUser(Long userId, Integer page);

    public Resource getPhotoFile(String filename);

    public Resource getPhotoThumbnailFile(String filename);

    void deletePhoto(String filename);

    void deleteAllPhotos(String email);

    PhotoMetadata getMetadata(Photo photo) throws IOException;

    void rotatePhoto(String filename) throws IOException;

    void updateMetadata(String filename, PhotoMetadata metadata) throws IOException;

    void favoritePhoto(String filename, boolean favorite);

    List<File> getPhotoFilesForExport(Long userId, List<Long> fileIds);

    Long getUsedStorage(Long userId);

    Long getStorageCapacity();
}
