package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.shared.model.ApplicationUser;

public interface ContactService {

    ContactList getContactList(ApplicationUser user);

    Contact addNewContact(String jwt, ContactDTO dto);

    Contact getContactByUser(String jwt, Long contactId);

    Contact editContact(String jwt, ContactDTO dto);
}
