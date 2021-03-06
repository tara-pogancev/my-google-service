package mygoogleserviceapi.photos.service;

import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import mygoogleserviceapi.photos.service.interfaces.ExifParser;
import mygoogleserviceapi.photos.service.interfaces.PhotoStorageService;
import mygoogleserviceapi.shared.config.FileStorageProperties;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PhotoStorageServiceImpl implements PhotoStorageService {
    private final Path fileStorageLocation;
    private static final String PHOTOS_DIRECTORY = "photos";
    private static final String THUMBNAIL_DIRECTORY = "thumbnail";

    @Autowired
    private final ExifParser exifParser;


    @Autowired
    public PhotoStorageServiceImpl(FileStorageProperties fileStorageProperties, ExifParser exifParser) {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + File.separator + fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.exifParser = exifParser;
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
        } catch (Exception ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public void savePhotoThumbnail(MultipartFile file, String email) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + fileName);
            }
            Files.createDirectories(Paths.get(this.fileStorageLocation + File.separator + PHOTOS_DIRECTORY + File.separator + email + File.separator + THUMBNAIL_DIRECTORY));
            Path targetLocation = this.fileStorageLocation.resolve(PHOTOS_DIRECTORY + File.separator + email + File.separator + THUMBNAIL_DIRECTORY + File.separator + fileName);


            BufferedImage newImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImg = Scalr.resize(newImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 420, 220, Scalr.OP_ANTIALIAS);
            String format = file.getContentType().equals("image/png") ? "PNG" : "JPG";
            ImageIO.write(resizedImg, format, targetLocation.toFile());

        } catch (Exception ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Resource getPhoto(String fileName, String email) {
        try {
            Path filePath = getPhotoPath(fileName, email);
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
    public Resource getPhotoThumbnail(String fileName, String email) {
        try {
            Path filePath = getThumbnailPath(fileName, email);
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
    public PhotoMetadata getMetadata(Photo photo) throws IOException {
        File photoFile = getPhoto(photo.getFileName(), photo.getApplicationUser().getEmail()).getFile();
        Double latitude = exifParser.getLat(photoFile);
        Double longitude = exifParser.getLong(photoFile);
        return new PhotoMetadata(latitude, longitude);
    }

    @Override
    public void setMetadata(Photo photo, PhotoMetadata metadata) throws IOException {
        File photoFile = getPhoto(photo.getFileName(), photo.getApplicationUser().getEmail()).getFile();
        exifParser.setLat(photoFile, metadata.getLatitude());
        exifParser.setLong(photoFile, metadata.getLongitude());
    }

    @Override
    public LocalDateTime getCreationDate(String fileName, String email) throws IOException {
        File file2 = getPhoto(fileName, email).getFile();
        return exifParser.getCreationDate(file2);
    }

    @Override
    public void deletePhoto(String fileName, String email) {
        Path filePath = getPhotoPath(fileName, email);
        try {
            Files.delete(filePath);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void deleteThumbnail(String fileName, String email) {
        Path filePath = getThumbnailPath(fileName, email);
        try {
            Files.delete(filePath);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void deleteAllPhotos(String email) {
        Path filePath = this.fileStorageLocation.resolve(PHOTOS_DIRECTORY + File.separator + email).normalize();
        try {
            Files.walk(filePath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.delete(filePath);
        } catch (Exception ignored) {
        }
    }


    private Path getPhotoPath(String fileName, String email) {
        return this.fileStorageLocation.resolve(PHOTOS_DIRECTORY + File.separator + email + File.separator + fileName).normalize();
    }

    private Path getThumbnailPath(String fileName, String email) {
        return this.fileStorageLocation.resolve(PHOTOS_DIRECTORY + File.separator + email + File.separator + THUMBNAIL_DIRECTORY + File.separator + fileName).normalize();
    }
}
