package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.validator.annotation.ValidContactPicture;
import mygoogleserviceapi.shared.converter.DataConverter;
import mygoogleserviceapi.shared.dto.response.PictureResponseDTO;
import mygoogleserviceapi.shared.validator.annotation.ValidProfilePicture;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactListController {

    private final DataConverter converter;
    private final ContactListService contactListService;

    @GetMapping("/{id}/contact-picture")
    public ResponseEntity<Resource> getContactPicture(@PathVariable Long id, HttpServletRequest request) {
        Resource resource = contactListService.getContactPicture(id);
        String contentType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/{contactId}/contact-picture")
    public ResponseEntity<PictureResponseDTO> postContactPicture(@PathVariable Long contactId, @RequestPart("file") @ValidContactPicture MultipartFile file, @RequestHeader(name = "Authorization") String jwt) {
        String fileName = "";
        fileName = contactListService.saveContactPicture(file, contactId, jwt);
        return ResponseEntity.ok(new PictureResponseDTO(fileName, file.getContentType(), file.getSize()));
    }

    @PutMapping("/contact-picture")
    public ResponseEntity<?> checkIfPictureIsValid(@RequestPart("file") @ValidContactPicture MultipartFile file) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{contactId}/contact-picture")
    public ResponseEntity<Resource> deleteContactPicture(@PathVariable Long contactId, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = contactListService.deleteContactPicture(contactId, jwt);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContactList(@RequestHeader(name = "Authorization") String jwt) {
        List<Contact> contacts = contactListService.getContacts(jwt);
        return new ResponseEntity<>(converter.convert(contacts, ContactDTO.class), HttpStatus.OK);
    }

    @PutMapping("/star/{id}")
    public ResponseEntity<?> starContact(@PathVariable Long id, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = contactListService.starContact(jwt, id);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/unstar/{id}")
    public ResponseEntity<?> unstarContact(@PathVariable Long id, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = contactListService.unstarContact(jwt, id);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = contactListService.deleteContact(jwt, id);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteContactList(@RequestBody List<Long> idList, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = contactListService.deleteContactList(jwt, idList);
        if (success) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> addNewContact(@RequestBody ContactDTO dto, @RequestHeader(name = "Authorization") String jwt) {
        Contact newContact = contactListService.addNewContact(jwt, dto);
        if (newContact != null) {
            return new ResponseEntity<>(converter.convert(newContact, ContactDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
