package mygoogleserviceapi.shared.controller;


import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.UserPhoneNumber;
import mygoogleserviceapi.shared.converter.DataConverter;
import mygoogleserviceapi.shared.dto.ChangePasswordDTO;
import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.dto.UserPhoneNumberDTO;
import mygoogleserviceapi.shared.dto.response.ProfilePictureResponseDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.UserPhoneNumberService;
import mygoogleserviceapi.shared.validator.annotation.ValidProfilePicture;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ApplicationUserController {

    private final DataConverter converter;
    private final UserPhoneNumberService userPhoneNumberService;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        ApplicationUser user = applicationUserService.getById(id);
        if (user != null) {
            return new ResponseEntity<>(converter.convert(user, UserDTO.class), HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change-name")
    public ResponseEntity<?> changeName(@RequestBody UserDTO dto, @RequestHeader(name = "Authorization") String jwt) {
        ApplicationUser changedUser = applicationUserService.changeName(dto, jwt);
        if (changedUser != null) {
            return new ResponseEntity<>(converter.convert(changedUser, UserDTO.class), HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO dto, @RequestHeader(name = "Authorization") String jwt) throws Exception {
        ApplicationUser changedUser = applicationUserService.changePassword(dto, jwt);
        if (changedUser != null) {
            return new ResponseEntity<>(converter.convert(changedUser, UserDTO.class), HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/change-phone-number")
    public ResponseEntity<?> changeUserPhoneNumber(@RequestBody UserPhoneNumberDTO dto, @RequestHeader(name = "Authorization") String jwt) throws Exception {
        UserPhoneNumber phoneNumber = userPhoneNumberService.changePhoneNumber(dto, jwt);
        if (phoneNumber != null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-phone-number")
    public ResponseEntity<?> addUserPhoneNumber(@RequestBody UserPhoneNumberDTO dto, @RequestHeader(name = "Authorization") String jwt) throws Exception {
        UserPhoneNumber phoneNumber = userPhoneNumberService.addPhoneNumber(dto, jwt);
        if (phoneNumber != null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete-phone-number")
    public ResponseEntity<?> deleteUserPhoneNumber(@RequestBody UserPhoneNumberDTO dto, @RequestHeader(name = "Authorization") String jwt) throws Exception {
        Boolean success = userPhoneNumberService.deletePhoneNumber(dto, jwt);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/set-default-application/{app}")
    public ResponseEntity<?> setDefaultApplication(@PathVariable String app, @RequestHeader(name = "Authorization") String jwt) throws Exception {
        ApplicationUser user = applicationUserService.setDefaultApplication(app, jwt);
        if (user != null) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}

