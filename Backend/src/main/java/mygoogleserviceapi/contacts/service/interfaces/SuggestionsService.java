package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.dto.SuggestionDTO;
import mygoogleserviceapi.contacts.model.Suggestion;

import java.util.List;

public interface SuggestionsService {

    List<Suggestion> getSuggestions(String jwt);

    List<SuggestionDTO> convertListToDTO(List<Suggestion> suggestions);

}
