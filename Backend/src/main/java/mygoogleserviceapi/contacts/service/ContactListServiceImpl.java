package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactListServiceImpl implements ContactListService {

    private final ApplicationUserService userService;

    @Override
    public ContactList getContactList(String jwt) {
        return null;
    }

    @Override
    public ContactList getContactList(ApplicationUser user) {
        return null;
    }

    @Override
    public List<Contact> getContacts(String jwt) {
        return null;
    }

    @Override
    public List<Contact> getDeletedContacts(String jwt) {
        return null;
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
