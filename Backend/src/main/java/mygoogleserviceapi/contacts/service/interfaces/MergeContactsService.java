package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.model.Contact;

import java.util.List;

public interface MergeContactsService {

    Contact mergeContacts(String jwt, List<Long> contactIds);

}
