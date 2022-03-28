package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.dto.SuggestionDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.Suggestion;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.SuggestionsService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionsServiceImpl implements SuggestionsService {

    private final DataConverter converter;
    private final ContactListService contactListService;


    @Override
    public List<Suggestion> getSuggestions(String jwt) {
        List<Suggestion> suggestions = new ArrayList<>();
        List<Contact> allContacts = contactListService.getContacts(jwt);
        List<Contact> unusedContacts = contactListService.getContacts(jwt);

        for (Contact contact : allContacts) {

            if (!unusedContacts.contains(contact)) {
                break;
            }

            List<Contact> currentSuggestion = new ArrayList<>();
            currentSuggestion.add(contact);
            for (Contact contactToCompare : unusedContacts) {
                if (contact.getFullName().equals(contactToCompare.getFullName())
                        && contact.getId() != contactToCompare.getId()) {
                    currentSuggestion.add(contactToCompare);
                }
            }

            if (currentSuggestion.size() > 1) {
                for (Contact contactToAdjust : currentSuggestion) {
                    unusedContacts.remove(contactToAdjust);
                }
                Suggestion suggestion = new Suggestion();
                suggestion.setContacts(currentSuggestion);
                suggestions.add(suggestion);
            }

        }

        return suggestions;
    }

    @Override
    public List<SuggestionDTO> convertListToDTO(List<Suggestion> suggestions) {
        List<SuggestionDTO> dtoList = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            SuggestionDTO dto = new SuggestionDTO();
            List<ContactDTO> contactDTOList = new ArrayList<>();
            for (Contact contact : suggestion.getContacts()) {
                contactDTOList.add(converter.convert(contact, ContactDTO.class));
            }
            dto.setContacts(contactDTOList);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
