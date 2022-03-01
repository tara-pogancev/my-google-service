package mygoogleserviceapi.contacts.service;

import mygoogleserviceapi.contacts.service.interfaces.ContactPictureStorageService;
import mygoogleserviceapi.shared.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service

public class ContactPictureStorageServiceImpl implements ContactPictureStorageService {

    private final Path fileStorageLocation;
    private static final String CONTACTS_DIRECTORY = "contacts";

    @Autowired
    public ContactPictureStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + File.separator + fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(Paths.get(this.fileStorageLocation + File.separator + CONTACTS_DIRECTORY));
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    @Override
    public String storeContactPicture(MultipartFile file, Long contactId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(CONTACTS_DIRECTORY + File.separator + contactId);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource loadContactPicture(Long contactId) {
        try {
            Path filePath = this.fileStorageLocation.resolve(CONTACTS_DIRECTORY + File.separator + contactId).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    @Override
    public void deleteContactPicture(Long contactId) {
        try {
            Path filePath = this.fileStorageLocation.resolve(CONTACTS_DIRECTORY + File.separator + contactId).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                Files.delete(filePath);
                return;
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
