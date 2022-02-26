package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.contacts.repository.ContactEmailAddressRepository;
import mygoogleserviceapi.contacts.repository.ContactListRepository;
import mygoogleserviceapi.contacts.repository.ContactPhoneNumberRepository;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactListServiceImpl implements ContactListService {

    private final ApplicationUserService userService;
    private final ContactRepository contactRepository;
    private final ContactListRepository contactListRepository;
    private final ContactPhoneNumberRepository contactPhoneNumberRepository;
    private final ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    public ContactList getContactList(String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (user != null) {
            return contactListRepository.getByUserId(user.getId());
        } return null;
    }

    @Override
    public ContactList getContactList(ApplicationUser user) {
        return contactListRepository.getByUserId(user.getId());
    }

    @Override
    public List<Contact> getContacts(String jwt) {
        ContactList contactList = getContactList(jwt);
        if (contactList != null) {
            List<Contact> contactsArrayList = contactList.getContacts();
            return contactsArrayList.stream().filter(c -> !c.getDeleted()).collect(Collectors.toList());
        } return null;
    }

    @Override
    public List<Contact> getDeletedContacts(String jwt) {
        ContactList contactList = getContactList(jwt);
        if (contactList != null) {
            List<Contact> contactsArrayList = contactList.getContacts();
            return contactsArrayList.stream().filter(c -> c.getDeleted()).collect(Collectors.toList());
        } return null;
    }

    @Override
    public Contact addNewContact(String jwt, ContactDTO dto) {
        return null;
    }

    @Override
    public Boolean deleteContact(String jwt, Long id) {
        return null;
    }

    @Override
    public Boolean deleteContactFromTrash(String jwt, Long id) {
        return null;
    }

    @Override
    public Boolean deleteAllContactsFromTrash(String jwt) {
        return null;
    }
}
