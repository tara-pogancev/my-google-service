package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import mygoogleserviceapi.contacts.repository.ContactEmailAddressRepository;
import mygoogleserviceapi.contacts.repository.ContactPhoneNumberRepository;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.BinService;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.ContactService;
import mygoogleserviceapi.contacts.service.interfaces.MergeContactsService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MergeContactsServiceImpl implements MergeContactsService {

    private final BinService binService;
    private final ContactService contactService;
    private final ApplicationUserService userService;
    private final ContactRepository contactRepository;
    private final ContactListService contactListService;
    private final ContactPhoneNumberRepository contactPhoneNumberRepository;
    private final ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    public Contact mergeContacts(String jwt, List<Long> contactIds) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (contactListService.contactListBelongsToUser(jwt, contactIds) && user != null) {

            // 1. Get candidate by profile picture
            Contact contact = new Contact();
            for (Long id : contactIds) {
                Resource resource = contactListService.getContactPicture(id);
                if (resource != null) {
                    contact = contactService.getContact(id);
                    break;
                }
            }

            // 2. If no candidate, set to first in list
            if (contact.getId() == null) {
                contact = contactService.getContact(contactIds.get(0));
            }

            // 3. Remove that id from the list
            contactIds.remove(contact.getId());

            // 4. Append all contacts
            for (Long id : contactIds) {
                Contact contactToMerge = contactService.getContact(id);
                mergeEmailAddresses(contact, contactToMerge);
                mergePhoneNumbers(contact, contactToMerge);
                contact = contactService.getContact(contact.getId());

                if (contactToMerge.getStarred() && !contact.getStarred()) {
                    contact.setStarred(true);
                    contactRepository.save(contact);
                }

                // 5. Delete other contacts
                binService.deleteContact(jwt, id);
            }

            // 6. return merged contact
            return contact;

        } else {
            return null;
        }
    }

    private void mergePhoneNumbers(Contact contact, Contact contactToMerge) {
        for (ContactPhoneNumber phoneNumber : contactToMerge.getPhoneNumbers()) {
            if (!contact.hasMatchingPhoneNumber(phoneNumber.getPhoneNumber())) {
                ContactPhoneNumber newPhoneNumber = new ContactPhoneNumber();
                newPhoneNumber.setPhoneNumber(phoneNumber.getPhoneNumber());
                newPhoneNumber.setType(phoneNumber.getType());
                newPhoneNumber.setContact(contact);
                contactPhoneNumberRepository.save(newPhoneNumber);
            }
        }
    }

    private void mergeEmailAddresses(Contact contact, Contact contactToMerge) {
        for (ContactEmailAddress emailAddress : contactToMerge.getEmailAddresses()) {
            if (!contact.hasMatchingEmail(emailAddress.getEmail())) {
                ContactEmailAddress newEmailAddress = new ContactEmailAddress();
                newEmailAddress.setEmail(emailAddress.getEmail());
                newEmailAddress.setType(emailAddress.getType());
                newEmailAddress.setContact(contact);
                contactEmailAddressRepository.save(newEmailAddress);
            }
        }
    }

}
