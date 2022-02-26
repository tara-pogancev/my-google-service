package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactPhoneNumberDTO {
    public Long id;
    public Long contactId;
    public String phoneNumber;
    public String type;

}
