package mygoogleserviceapi.contacts.dto;

import lombok.Data;

import java.util.List;

@Data
public class SuggestionDTO {
    public List<ContactDTO> contacts;

}
