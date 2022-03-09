package mygoogleserviceapi.contacts.controller;


import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.ContactService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final DataConverter converter;
    private final ContactService contactService;

    @PostMapping("/new")
    public ResponseEntity<?> addNewContact(@RequestBody ContactDTO dto, @RequestHeader(name = "Authorization") String jwt) {
        Contact newContact = contactService.addNewContact(jwt, dto);
        if (newContact != null) {
            return new ResponseEntity<>(converter.convert(newContact, ContactDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContact(@PathVariable Long contactId, @RequestHeader(name = "Authorization") String jwt) {
        Contact contact = contactService.getContactByUser(jwt, contactId);
        if (contact != null) {
            return new ResponseEntity<>(converter.convert(contact, ContactDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping
    public ResponseEntity<?> editContact(@RequestBody ContactDTO dto, @RequestHeader(name = "Authorization") String jwt) {
        Contact contact = contactService.editContact(jwt, dto);
        if (contact != null) {
            return new ResponseEntity<>(converter.convert(contact, ContactDTO.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
