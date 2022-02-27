package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactEmailAddressDTO implements Comparable<ContactEmailAddressDTO>{
    public Long id;
    public Long contactId;
    public String email;
    public String type;

    @Override
    public int compareTo(ContactEmailAddressDTO o) {
        if (email == null || o.email == null) {
            return 0;
        }
        return email.compareTo(o.email);
    }
}
