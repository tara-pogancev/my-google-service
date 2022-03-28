package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactListService {

    ContactList getContactList(String jwt);

    List<Contact> getContacts(String jwt);

    Boolean deleteContact(String jwt, Long id);

    Resource getContactPicture(Long id);

    Boolean starContact(String jwt, Long id);

    Boolean unstarContact(String jwt, Long id);

    Boolean contactBelongsToUser(String jwt, Long contactId);

    Boolean contactListBelongsToUser(String jwt, List<Long> contactIds);

    Boolean deleteContactList(String jwt, List<Long> idList);

    String saveContactPicture(MultipartFile file, Long contactId, String jwt);

    Boolean deleteContactPicture(Long contactId, String jwt);
}
