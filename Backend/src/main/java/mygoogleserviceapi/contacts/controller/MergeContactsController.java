package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.MergeContactsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/merge")
public class MergeContactsController {

    private final MergeContactsService mergeContactsService;

    @PutMapping
    public ResponseEntity<?> mergeContacts(@RequestBody List<Long> ids, @RequestHeader(name = "Authorization") String jwt) {
        Contact mergedContact = mergeContactsService.mergeContacts(jwt, ids);
        if (mergedContact != null) {
            return new ResponseEntity<>(mergedContact.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
