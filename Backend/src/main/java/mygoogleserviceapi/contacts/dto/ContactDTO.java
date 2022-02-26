package mygoogleserviceapi.contacts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactDTO {
    public Long id;
    public Long contactOwnerId;
    public String firstName;
    public String lastName;
    public String fullName;
    public Boolean starred;
    public Boolean deleted;
    public List<ContactEmailAddressDTO> emails;
    public List<ContactPhoneNumberDTO> phoneNumbers;

}
