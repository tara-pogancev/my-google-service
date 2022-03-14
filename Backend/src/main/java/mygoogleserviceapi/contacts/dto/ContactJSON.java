package mygoogleserviceapi.contacts.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactJSON {
    public String firstName;
    public String lastName;
    public Boolean starred;
    public Boolean deleted;
    public List<ContactEmailAddressJSON> emails;
    public List<ContactPhoneNumberJSON> phoneNumbers;
}
