package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.service.interfaces.ImportContactsService;
import mygoogleserviceapi.contacts.validator.annotation.ValidContactPicture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/import")
public class ImportContactsController {

    private final ImportContactsService importContactsService;

    @PostMapping
    public ResponseEntity<?> importFromFile(@RequestPart("file") @ValidContactPicture MultipartFile file, @RequestHeader(name = "Authorization") String jwt) throws IOException {
        if (file.getContentType().equals("text/csv")) {
            importContactsService.importFromCSV(file, jwt);
        } else if (file.getContentType().equals("text/json")) {
            importContactsService.importFromJSON(file, jwt);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
