package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import mygoogleserviceapi.contacts.repository.ContactEmailAddressRepository;
import mygoogleserviceapi.contacts.repository.ContactPhoneNumberRepository;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.ContactAppUserService;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.ContactService;
import mygoogleserviceapi.contacts.service.interfaces.CreateContactsFromImportService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateContactsFromImportServiceImpl implements CreateContactsFromImportService {

    private final ContactService contactService;
    private final ApplicationUserService userService;
    private final ContactRepository contactRepository;
    private final ContactListService contactListService;
    private final ContactAppUserService contactAppUserService;
    private final ContactPhoneNumberRepository contactPhoneNumberRepository;
    private final ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    public void createFromImportedContact(String jwt, Contact contact) {
        ApplicationUser owner = userService.getUserByJwt(jwt);
        if (owner != null) {
            contact.setContactList(contactService.getContactList(owner));
            contact = contactRepository.save(contact);

            Set<ContactEmailAddress> emailAddresses = new HashSet<>();
            for (ContactEmailAddress email : contact.getEmailAddresses()) {
                email.setContact(contact);
                email = contactEmailAddressRepository.save(email);
                emailAddresses.add(email);
            }

            Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();
            for (ContactPhoneNumber phoneNumber : contact.getPhoneNumbers()) {
                phoneNumber.setContact(contact);
                phoneNumber = contactPhoneNumberRepository.save(phoneNumber);
                phoneNumbers.add(phoneNumber);
            }

            contact.setEmailAddresses(emailAddresses);
            contact.setPhoneNumbers(phoneNumbers);
            contactAppUserService.checkForApplicationEmailMatch(contact.getId());
        }
    }

    @Override
    public Boolean userHasIdenticalContact(String jwt, Contact contact) {
        List<Contact> contactList = contactListService.getContacts(jwt);
        for (Contact userContact : contactList) {
            if (contact.isContactMatching(userContact)) {
                return true;
            }
        }

        return false;
    }
}
