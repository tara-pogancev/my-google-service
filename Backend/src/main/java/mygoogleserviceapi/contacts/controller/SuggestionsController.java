package mygoogleserviceapi.contacts.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Suggestion;
import mygoogleserviceapi.contacts.service.interfaces.SuggestionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestions")
public class SuggestionsController {

    private final SuggestionsService suggestionsService;

    @GetMapping
    public ResponseEntity<?> mergeContacts(@RequestHeader(name = "Authorization") String jwt) {
        List<Suggestion> suggestions = suggestionsService.getSuggestions(jwt);
        return new ResponseEntity<>(suggestionsService.convertListToDTO(suggestions), HttpStatus.OK);
    }

}
