package mygoogleserviceapi.shared.controller;


import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.dto.response.ProfilePictureResponseDTO;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.validator.annotation.ValidProfilePicture;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<ProfilePictureResponseDTO> postProfilePicture(@PathVariable Long id, @RequestPart("file") @ValidProfilePicture MultipartFile file) {
        String fileName = "";
        fileName = applicationUserService.saveProfilePicture(file, id);
        return ResponseEntity.ok(new ProfilePictureResponseDTO(fileName, file.getContentType(), file.getSize()));
    }

    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Long id, HttpServletRequest request) {
        Resource resource = applicationUserService.getProfilePicture(id);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}

