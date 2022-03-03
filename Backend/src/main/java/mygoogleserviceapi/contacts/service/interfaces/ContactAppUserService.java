package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.model.Contact;

import java.util.List;

public interface ContactAppUserService {

    void checkForApplicationEmailMatch(Long contactId);

    List<Contact> findContactWithEmail(String email);

    void refreshContactAppUserList(List<Contact> contacts);

    void refreshContactAppUserByEmail(String email);

    void removeAppUserFromContact(Long contactId);

    void removeAppUserFromContactList(List<Contact> contacts);

}
