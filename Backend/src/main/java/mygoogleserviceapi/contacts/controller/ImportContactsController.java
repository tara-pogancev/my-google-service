package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.service.interfaces.ImportContactsService;
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
    public ResponseEntity<?> importFromFile(@RequestPart("file") MultipartFile file, @RequestHeader(name = "Authorization") String jwt) throws IOException {
        System.out.println(file.getContentType());
        if (file.getContentType().equals("text/csv")) {
            importContactsService.importFromCSV(file, jwt);
        } else if (file.getContentType().equals("application/json")) {
            importContactsService.importFromJSON(file, jwt);
        } else {
            return new ResponseEntity<>(null, HttpStatus.I_AM_A_TEAPOT);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
