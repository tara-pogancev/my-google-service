package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.BinService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bin")
public class BinController {

    private final BinService binService;
    private final DataConverter converter;

    @GetMapping("/all")
    public ResponseEntity<?> getBinContactList(@RequestHeader(name = "Authorization") String jwt) {
        List<Contact> contacts = binService.getDeletedContacts(jwt);
        return ResponseEntity.ok(converter.convert(contacts, ContactDTO.class));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBinContact(@PathVariable Long id, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = binService.deleteContact(jwt, id);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<?> deleteBinContactList(@RequestBody List<Long> idList, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = binService.deleteContactList(idList, jwt);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete-bin")
    public ResponseEntity<?> deleteAllContactsInBin(@RequestHeader(name = "Authorization") String jwt) {
        Boolean success = binService.deleteAllContactsInBin(jwt);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/recover/{id}")
    public ResponseEntity<?> recoverBinContact(@PathVariable Long id, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = binService.recoverContact(jwt, id);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/recover")
    public ResponseEntity<?> recoverBinContactList(@RequestBody List<Long> idList, @RequestHeader(name = "Authorization") String jwt) {
        Boolean success = binService.recoverContactList(idList, jwt);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
