package mygoogleserviceapi.photos.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.photos.dto.request.PhotoMetadataRequestDTO;
import mygoogleserviceapi.photos.dto.request.UpdateFavoritePhotoRequestDTO;
import mygoogleserviceapi.photos.dto.request.UserInfoRequestDTO;
import mygoogleserviceapi.photos.dto.response.AddPhotoResponseDTO;
import mygoogleserviceapi.photos.dto.response.PhotoInfoResponseDTO;
import mygoogleserviceapi.photos.dto.response.PhotoStorageInfoResponseDTO;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final DataConverter converter;
    private final PhotoService photoService;

    @PostMapping()
    public ResponseEntity<List<AddPhotoResponseDTO>> addPhoto(@RequestPart("files") MultipartFile[] files,
                                                              @RequestPart("info") @Valid UserInfoRequestDTO userInfoRequestDTO) throws IOException {
        List<AddPhotoResponseDTO> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            Photo photo = photoService.savePhoto(file, userInfoRequestDTO.getEmail());
            if (photo == null)
                photos.add(new AddPhotoResponseDTO(null, file.getOriginalFilename(), false));
            else {
                photos.add(new AddPhotoResponseDTO(photo.getId(), file.getOriginalFilename(), true));
            }
        }
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<PhotoInfoResponseDTO>> getPhotos(@PathVariable Long id, @RequestParam(required = false) Integer page) throws IOException {
        List<PhotoInfoResponseDTO> responseDTOS = new ArrayList<>();
        List<Photo> photos = photoService.getPhotosForUser(id, page);
        for (Photo photo : photos) {
            PhotoMetadata metadata = photoService.getMetadata(photo);
            responseDTOS.add(new PhotoInfoResponseDTO(photo.getId(),
                    photo.getFileName(),
                    metadata.getLatitude(),
                    metadata.getLongitude(),
                    photo.getCreationDate(),
                    photo.isFavorite(),
                    photo.getSize()));
            photoService.getMetadata(photo);
        }
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/users/{id}/storage")
    public ResponseEntity<PhotoStorageInfoResponseDTO> getStorage(@PathVariable Long id) {
        Long usedStorage = photoService.getUsedStorage(id);
        Long storageCapacity = photoService.getStorageCapacity();
        PhotoStorageInfoResponseDTO responseDTO = new PhotoStorageInfoResponseDTO(storageCapacity, usedStorage);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
        Resource resource = photoService.getPhotoFile(filename);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/{filename}/thumbnail")
    public ResponseEntity<Resource> getPhotoThumbnail(@PathVariable String filename) {
        Resource resource = photoService.getPhotoThumbnailFile(filename);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PutMapping("/{filename}/rotate")
    public ResponseEntity<HttpStatus> rotatePhoto(@PathVariable String filename) throws IOException {
        photoService.rotatePhoto(filename);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{filename}/favorite")
    public ResponseEntity<HttpStatus> favoritePhoto(@PathVariable String filename, @RequestBody @Valid UpdateFavoritePhotoRequestDTO updateFavoritePhotoRequestDTO) {
        photoService.favoritePhoto(filename, updateFavoritePhotoRequestDTO.getFavorite());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{filename}/metadata")
    public ResponseEntity<HttpStatus> updateMetadata(@PathVariable String filename, @RequestBody @Valid PhotoMetadataRequestDTO metadataRequestDTO) throws IOException {
        PhotoMetadata metadata = converter.convert(metadataRequestDTO, PhotoMetadata.class);
        photoService.updateMetadata(filename, metadata);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deletePhoto(@PathVariable String filename) {
        photoService.deletePhoto(filename);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/users/{id}/export")
    public ResponseEntity<byte[]> exportZip(@PathVariable Long id, @RequestParam(required = false) List<Long> fileIds) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        List<File> files = photoService.getPhotoFilesForExport(id, fileIds);

        for (File file : files) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/zip"))
                .header("Content-Disposition", "attachment; filename=\"export.zip\"")
                .body(byteArrayOutputStream.toByteArray());
    }
}
