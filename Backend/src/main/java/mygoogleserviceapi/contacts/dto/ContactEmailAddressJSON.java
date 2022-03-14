package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactEmailAddressJSON implements Comparable<ContactEmailAddressJSON> {

    public String email;
    public String type;

    @Override
    public int compareTo(ContactEmailAddressJSON o) {
        if (email == null || o.email == null) {
            return 0;
        }
        return email.compareTo(o.email);
    }
}
