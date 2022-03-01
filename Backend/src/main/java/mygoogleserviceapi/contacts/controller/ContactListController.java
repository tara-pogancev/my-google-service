package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


}
