package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.model.Contact;

public interface CreateContactsFromImportService {

    void createFromImportedContact(String jwt, Contact contact);

    Boolean userHasIdenticalContact(String jwt, Contact contact);

}
