package mygoogleserviceapi.contacts.converter;

import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.dto.ContactEmailAddressDTO;
import mygoogleserviceapi.contacts.dto.ContactPhoneNumberDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ContactToDTO implements Converter<Contact, ContactDTO> {

    @Override
    public ContactDTO convert(Contact source) {
        ContactDTO dto = new ContactDTO();
        dto.setId(source.getId());
        if (source.getContactList() != null) {
            dto.setContactOwnerId(source.getContactList().getOwner().getId());
        }
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setFullName(source.getFullName());
        dto.setStarred(source.getStarred());
        dto.setDeleted(source.getDeleted());
        dto.setPhoneNumbers(getPhoneNumbers(source));
        dto.setEmails(getEmails(source));
        if (source.getContactApplicationUser() != null) {
            dto.setAppUserEmail(source.getContactApplicationUser().getEmail());
        }
        return dto;
    }

    private List<ContactPhoneNumberDTO> getPhoneNumbers(Contact contact) {
        List<ContactPhoneNumberDTO> list = new ArrayList<>();
        for (ContactPhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            ContactPhoneNumberDTO dto = new ContactPhoneNumberDTO();
            dto.setPhoneNumber(phoneNumber.getPhoneNumber());
            dto.setType(phoneNumber.getType().getString());
            dto.setId(phoneNumber.getId());
            dto.setContactId(contact.getId());
            list.add(dto);
        }

        Collections.sort(list);
        return list;
    }

    private List<ContactEmailAddressDTO> getEmails(Contact contact) {
        List<ContactEmailAddressDTO> list = new ArrayList<>();
        for (ContactEmailAddress emailAddress : contact.getEmailAddresses()) {
            ContactEmailAddressDTO dto = new ContactEmailAddressDTO();
            dto.setEmail(emailAddress.getEmail());
            dto.setType(emailAddress.getType().getString());
            dto.setId(emailAddress.getId());
            dto.setContactId(contact.getId());
            list.add(dto);
        }

        Collections.sort(list);
        return list;
    }

}
