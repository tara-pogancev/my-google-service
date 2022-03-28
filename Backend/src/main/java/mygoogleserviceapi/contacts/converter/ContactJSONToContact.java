package mygoogleserviceapi.contacts.converter;

import mygoogleserviceapi.contacts.enumeration.ContactTypeEnum;
import mygoogleserviceapi.contacts.json.ContactEmailAddressJSON;
import mygoogleserviceapi.contacts.json.ContactJSON;
import mygoogleserviceapi.contacts.json.ContactPhoneNumberJSON;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContactJSONToContact implements Converter<ContactJSON, Contact> {
    @Override
    public Contact convert(ContactJSON source) {
        Contact contact = new Contact();
        contact.setFirstName(source.getFirstName());
        contact.setLastName(source.getLastName());
        contact.setStarred(source.getStarred());
        contact.setDeleted(source.getDeleted());
        contact.setEmailAddresses(getEmailAddresses(source.getEmails()));
        contact.setPhoneNumbers(getPhoneNumbers(source.getPhoneNumbers()));
        return contact;
    }

    private Set<ContactEmailAddress> getEmailAddresses(List<ContactEmailAddressJSON> emailAddresses) {
        Set<ContactEmailAddress> contactEmailAddresses = new HashSet<>();
        for (ContactEmailAddressJSON emailAddress : emailAddresses) {
            ContactEmailAddress newEmailAddress = new ContactEmailAddress();
            newEmailAddress.setType(ContactTypeEnum.getEnumFromString(emailAddress.getType()));
            newEmailAddress.setEmail(emailAddress.getEmail());
            contactEmailAddresses.add(newEmailAddress);
        }
        return contactEmailAddresses;
    }

    private Set<ContactPhoneNumber> getPhoneNumbers(List<ContactPhoneNumberJSON> phoneNumbers) {
        Set<ContactPhoneNumber> contactPhoneNumbers = new HashSet<>();
        for (ContactPhoneNumberJSON phoneNumber : phoneNumbers) {
            ContactPhoneNumber newPhoneNumber = new ContactPhoneNumber();
            newPhoneNumber.setType(ContactTypeEnum.getEnumFromString(phoneNumber.getType()));
            newPhoneNumber.setPhoneNumber(phoneNumber.getPhoneNumber());
            contactPhoneNumbers.add(newPhoneNumber);
        }
        return contactPhoneNumbers;
    }
}
