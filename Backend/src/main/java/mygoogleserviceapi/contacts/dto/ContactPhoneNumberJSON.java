package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactPhoneNumberJSON implements Comparable<ContactPhoneNumberJSON> {

    public String phoneNumber;
    public String type;

    @Override
    public int compareTo(ContactPhoneNumberJSON o) {
        if (phoneNumber == null || o.phoneNumber == null) {
            return 0;
        }
        return phoneNumber.compareTo(o.phoneNumber);
    }
}
