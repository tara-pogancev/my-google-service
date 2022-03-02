package mygoogleserviceapi.photos.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.photos.dto.request.UserInfoRequestDTO;
import mygoogleserviceapi.photos.dto.response.PhotoResponseDTO;
import mygoogleserviceapi.photos.model.Photo;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping()
    public ResponseEntity<List<PhotoResponseDTO>> addPhoto(@RequestPart("files") MultipartFile[] files,
                                                           @RequestPart("info") @Valid UserInfoRequestDTO userInfoRequestDTO) {
        System.out.println(userInfoRequestDTO.getEmail());
        System.out.println(files);
        List<PhotoResponseDTO> photos = new ArrayList<>();
        for (MultipartFile file : files) {
            Photo photo = photoService.savePhoto(file, userInfoRequestDTO.getEmail());
            if (photo != null)
                System.out.println(photo.getFileName());
        }

        return ResponseEntity.ok(photos);
    }

}
