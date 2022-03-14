package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.shared.model.ApplicationUser;

import java.util.List;

public interface ContactService {

    ContactList getContactList(ApplicationUser user);

    Contact addNewContact(String jwt, ContactDTO dto);

    Contact getContact(Long contactId);

    Contact getContactByUser(String jwt, Long contactId);

    Contact editContact(String jwt, ContactDTO dto);

    List<Contact> getContactListByIdsByUser(String jwt, List<Long> ids);
}
