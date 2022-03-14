package mygoogleserviceapi.contacts.converter;

import mygoogleserviceapi.contacts.dto.ContactEmailAddressJSON;
import mygoogleserviceapi.contacts.dto.ContactJSON;
import mygoogleserviceapi.contacts.dto.ContactPhoneNumberJSON;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ContactToJSON implements Converter<Contact, ContactJSON> {

    @Override
    public ContactJSON convert(Contact source) {
        ContactJSON json = new ContactJSON();
        json.setFirstName(source.getFirstName());
        json.setLastName(source.getLastName());
        json.setStarred(source.getStarred());
        json.setDeleted(source.getDeleted());
        json.setPhoneNumbers(getPhoneNumbers(source));
        json.setEmails(getEmails(source));
        return json;
    }

    private List<ContactPhoneNumberJSON> getPhoneNumbers(Contact contact) {
        List<ContactPhoneNumberJSON> list = new ArrayList<>();
        for (ContactPhoneNumber phoneNumber : contact.getPhoneNumbers()) {
            ContactPhoneNumberJSON json = new ContactPhoneNumberJSON();
            json.setPhoneNumber(phoneNumber.getPhoneNumber());
            json.setType(phoneNumber.getType().getString());
            list.add(json);
        }

        Collections.sort(list);
        return list;
    }

    private List<ContactEmailAddressJSON> getEmails(Contact contact) {
        List<ContactEmailAddressJSON> list = new ArrayList<>();
        for (ContactEmailAddress emailAddress : contact.getEmailAddresses()) {
            ContactEmailAddressJSON json = new ContactEmailAddressJSON();
            json.setEmail(emailAddress.getEmail());
            json.setType(emailAddress.getType().getString());
            list.add(json);
        }

        Collections.sort(list);
        return list;
    }

}
