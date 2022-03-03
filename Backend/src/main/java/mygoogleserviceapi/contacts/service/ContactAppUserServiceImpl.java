package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.ContactAppUserService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactAppUserServiceImpl implements ContactAppUserService {

    private final ApplicationUserRepository userRepository;
    private final ContactRepository contactRepository;

    @Override
    public void checkForApplicationEmailMatch(Long contactId) {
        Contact contact = contactRepository.get(contactId);
        if (contact != null) {
            for (ContactEmailAddress email : contact.getEmailAddresses()) {
                ApplicationUser contactUser = userRepository.findUserByEmail(email.getEmail());
                if (contactUser != null) {
                    contact.setContactApplicationUser(contactUser);
                    contactRepository.save(contact);
                    break;
                }
            }
        }
    }

    @Override
    public List<Contact> findContactWithEmail(String email) {
        /*
        List<Contact> contactsWithEmailMatch = new ArrayList<>();
        for (Contact c : contactRepository.findAll()) {
            if (c.hasEmailAddress(email)) {
                contactsWithEmailMatch.add(c);
            }
        }

        return contactsWithEmailMatch;

        */
        return contactRepository.findAll().stream().filter(c ->
                c.hasEmailAddress(email)
        ).collect(Collectors.toList());
    }

    @Override
    public void refreshContactAppUserList(List<Contact> contacts) {
        for (Contact contact : contacts) {
            checkForApplicationEmailMatch(contact.getId());
        }
    }

    @Override
    public void refreshContactAppUserByEmail(String email) {
        List<Contact> contactsToRefresh = findContactWithEmail(email);
        refreshContactAppUserList(contactsToRefresh);
    }

    @Override
    public void removeAppUserFromContact(Long contactId) {
        Contact contact = contactRepository.get(contactId);
        if (contact != null) {
            contact.setContactApplicationUser(null);
            contactRepository.save(contact);
        }
    }

    @Override
    public void removeAppUserFromContactList(List<Contact> contacts) {
        for (Contact contact : contacts) {
            removeAppUserFromContact(contact.getId());
        }
    }

}
