package mygoogleserviceapi.contacts.converter;

import mygoogleserviceapi.contacts.csv.ContactSimpleCSV;
import mygoogleserviceapi.contacts.enumeration.ContactTypeEnum;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContactSimpleCSVToContact implements Converter<ContactSimpleCSV, Contact> {

    @Override
    public Contact convert(ContactSimpleCSV source) {
        Contact contact = new Contact();
        contact.setFirstName(source.getFirstName());
        contact.setLastName(source.getLastName());
        contact.setStarred(source.getStarred());
        contact.setDeleted(source.getDeleted());
        contact.setEmailAddresses(getEmailAddresses(source.getEmailAddresses()));
        contact.setPhoneNumbers(getPhoneNumbers(source.getPhoneNumbers()));
        return contact;
    }

    private Set<ContactEmailAddress> getEmailAddresses(List<String> emailAddresses) {
        Set<ContactEmailAddress> contactEmailAddresses = new HashSet<>();
        for (String emailAddress : emailAddresses) {
            ContactEmailAddress newEmailAddress = new ContactEmailAddress();
            newEmailAddress.setType(ContactTypeEnum.OTHER);
            newEmailAddress.setEmail(emailAddress);
            contactEmailAddresses.add(newEmailAddress);
        }
        return contactEmailAddresses;
    }

    private Set<ContactPhoneNumber> getPhoneNumbers(List<String> phoneNumbers) {
        Set<ContactPhoneNumber> contactPhoneNumbers = new HashSet<>();
        for (String phoneNumber : phoneNumbers) {
            ContactPhoneNumber newPhoneNumber = new ContactPhoneNumber();
            newPhoneNumber.setType(ContactTypeEnum.OTHER);
            newPhoneNumber.setPhoneNumber(phoneNumber);
            contactPhoneNumbers.add(newPhoneNumber);
        }
        return contactPhoneNumbers;
    }

}
