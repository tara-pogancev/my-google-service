package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactEmailAddressDTO {
    public Long id;
    public Long contactId;
    public String email;
    public String type;

}
