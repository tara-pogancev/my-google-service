package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.dto.ContactEmailAddressDTO;
import mygoogleserviceapi.contacts.dto.ContactPhoneNumberDTO;
import mygoogleserviceapi.contacts.enumeration.ContactTypeEnum;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import mygoogleserviceapi.contacts.repository.ContactEmailAddressRepository;
import mygoogleserviceapi.contacts.repository.ContactListRepository;
import mygoogleserviceapi.contacts.repository.ContactPhoneNumberRepository;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.ContactService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ApplicationUserService userService;
    private final ContactRepository contactRepository;
    private final ContactListService contactListService;
    private final ContactListRepository contactListRepository;
    private final ContactPhoneNumberRepository contactPhoneNumberRepository;
    private final ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    public Contact addNewContact(String jwt, ContactDTO dto) {
        ApplicationUser owner = userService.getUserByJwt(jwt);
        if (owner != null) {
            Contact contact = new Contact();
            contact.setFirstName(dto.firstName);
            contact.setLastName(dto.lastName);
            contact.setContactList(getContactList(owner));
            contact = contactRepository.save(contact);

            Set<ContactEmailAddress> emailAddresses = new HashSet<>();
            for (ContactEmailAddressDTO emailDTO : dto.getEmails()) {
                ContactEmailAddress email = new ContactEmailAddress();
                email.setContact(contact);
                email.setEmail(emailDTO.email.toLowerCase());
                email.setType(ContactTypeEnum.getEnumFromString(emailDTO.type));
                email = contactEmailAddressRepository.save(email);
                emailAddresses.add(email);
            }

            Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();
            for (ContactPhoneNumberDTO phoneNumberDTO : dto.getPhoneNumbers()) {
                ContactPhoneNumber phoneNumber = new ContactPhoneNumber();
                phoneNumber.setContact(contactRepository.get(contact.getId()));
                phoneNumber.setPhoneNumber(phoneNumberDTO.phoneNumber);
                phoneNumber.setType(ContactTypeEnum.getEnumFromString(phoneNumberDTO.type));
                phoneNumber = contactPhoneNumberRepository.save(phoneNumber);
                phoneNumbers.add(phoneNumber);
            }

            contact.setEmailAddresses(emailAddresses);
            contact.setPhoneNumbers(phoneNumbers);
            checkForApplicationEmailMatch(contact.getId());

            return contactRepository.get(contact.getId());

        } else {
            return null;
        }
    }

    private void checkForApplicationEmailMatch(Long contactId) {
        Contact contact = contactRepository.get(contactId);
        if (contact != null) {
            for (ContactEmailAddress email : contact.getEmailAddresses()) {
                ApplicationUser contactUser = userService.findByEmail(email.getEmail());
                if (contactUser != null) {
                    contact.setContactApplicationUser(contactUser);
                    contactRepository.save(contact);
                    break;
                }
            }
        }
    }

    @Override
    public Contact getContactByUser(String jwt, Long contactId) {
        if (contactListService.contactBelongsToUser(jwt, contactId)) {
            return contactRepository.get(contactId);
        } else {
            return null;
        }
    }

    @Override
    public ContactList getContactList(ApplicationUser user) {
        return contactListRepository.getByUserId(user.getId());
    }

}
