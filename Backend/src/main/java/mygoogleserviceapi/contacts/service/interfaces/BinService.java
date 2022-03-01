package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.model.Contact;

import java.util.List;

public interface BinService {

    List<Contact> getDeletedContacts(String jwt);

    Boolean deleteAllContactsInBin(String jwt);

    Boolean deleteContact(String jwt, Long id);

    Boolean deleteContactList(List<Long> idList, String jwt);

    Boolean recoverContactList(List<Long> idList, String jwt);

    Boolean recoverContact(String jwt, Long id);
}
