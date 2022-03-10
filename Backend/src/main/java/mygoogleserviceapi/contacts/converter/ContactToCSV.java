package mygoogleserviceapi.contacts.converter;

import mygoogleserviceapi.contacts.dto.ContactCSV;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactToCSV implements Converter<Contact, ContactCSV> {

    @Override
    public ContactCSV convert(Contact source) {
        ContactCSV csv = new ContactCSV();
        csv.setFirstName(source.getFirstName());
        csv.setLastName(source.getLastName());
        csv.setStarred(source.getStarred());
        csv.setDeleted(source.getDeleted());
        csv.setPhoneNumbers(getPhoneNumbers(source));
        csv.setEmails(getEmails(source));
        return csv;
    }

    private List<String> getPhoneNumbers(Contact contact) {
        List<String> list = new ArrayList<>();
        for (ContactPhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            list.add(phoneNumber.getPhoneNumber());
        }

        return list;
    }

    private List<String> getEmails(Contact contact) {
        List<String> list = new ArrayList<>();
        for (ContactEmailAddress emailAddress : contact.getEmailAddresses()) {
            list.add(emailAddress.getEmail());
        }

        return list;
    }

}
