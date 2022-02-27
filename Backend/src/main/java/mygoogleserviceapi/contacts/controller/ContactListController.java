package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.shared.converter.DataConverter;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
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

}
