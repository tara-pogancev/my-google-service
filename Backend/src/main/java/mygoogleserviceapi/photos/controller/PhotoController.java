package mygoogleserviceapi.photos.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.photos.dto.request.UserInfoRequestDTO;
import mygoogleserviceapi.photos.dto.response.AddPhotoResponseDTO;
import mygoogleserviceapi.photos.dto.response.PhotoInfoResponseDTO;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping()
    public ResponseEntity<List<AddPhotoResponseDTO>> addPhoto(@RequestPart("files") MultipartFile[] files,
                                                              @RequestPart("info") @Valid UserInfoRequestDTO userInfoRequestDTO) {
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
    public ResponseEntity<List<PhotoInfoResponseDTO>> getPhotos(@PathVariable Long id) {
        List<PhotoInfoResponseDTO> responseDTOS = new ArrayList<>();
        Set<Photo> photos = photoService.getPhotosForUser(id);
        for (Photo photo : photos) {
            responseDTOS.add(new PhotoInfoResponseDTO(photo.getId(), photo.getFileName()));
        }
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
        Resource resource = photoService.getPhotoFile(filename);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deletePhoto(@PathVariable String filename) {
        photoService.deletePhoto(filename);
        return ResponseEntity.noContent().build();
    }


}