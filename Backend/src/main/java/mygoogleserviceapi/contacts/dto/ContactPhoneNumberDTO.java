package mygoogleserviceapi.contacts.dto;

import lombok.Data;

@Data
public class ContactPhoneNumberDTO implements Comparable<ContactPhoneNumberDTO> {
    public Long id;
    public Long contactId;
    public String phoneNumber;
    public String type;

    @Override
    public int compareTo(ContactPhoneNumberDTO o) {
        if (phoneNumber == null || o.phoneNumber == null) {
            return 0;
        }
        return phoneNumber.compareTo(o.phoneNumber);
    }
}
