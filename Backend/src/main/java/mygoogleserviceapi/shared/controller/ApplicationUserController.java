package mygoogleserviceapi.shared.controller;


import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.dto.response.ProfilePictureResponseDTO;
import mygoogleserviceapi.shared.service.interfaces.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ApplicationUserController {

    private final FileStorageService fileStorageService;

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<ProfilePictureResponseDTO> postProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals("image/jpeg") || !file.getContentType().equals("image/png")) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = fileStorageService.storeProfilePicture(file, id);
        return new ProfilePictureResponseDTO(fileName, file.getContentType(), file.getSize());
    }

    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long id, HttpServletRequest request) {
        Resource resource = fileStorageService.loadProfilePicture(id);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}

