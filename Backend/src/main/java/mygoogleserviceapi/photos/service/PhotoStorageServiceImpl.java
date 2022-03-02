package mygoogleserviceapi.photos.service;

import mygoogleserviceapi.photos.service.interfaces.PhotoStorageService;
import mygoogleserviceapi.shared.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PhotoStorageServiceImpl implements PhotoStorageService {
    private final Path fileStorageLocation;
    private static final String PHOTOS_DIRECTORY = "photos";


    @Autowired
    public PhotoStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + File.separator + fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(Paths.get(this.fileStorageLocation + File.separator + PHOTOS_DIRECTORY));
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public MultipartFile savePhoto(MultipartFile file, String email) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + fileName);
            }
            Files.createDirectories(Paths.get(this.fileStorageLocation + File.separator + PHOTOS_DIRECTORY + File.separator + email));
            Path targetLocation = this.fileStorageLocation.resolve(PHOTOS_DIRECTORY + File.separator + email + File.separator + fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return file;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
