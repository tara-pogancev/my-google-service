package mygoogleserviceapi.contacts.service.interfaces;

import mygoogleserviceapi.contacts.dto.ContactDTO;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.shared.model.ApplicationUser;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactListService {

    ContactList getContactList(String jwt);

    ContactList getContactList(ApplicationUser user);

    List<Contact> getContacts(String jwt);

    Contact addNewContact(String jwt, ContactDTO dto);

    Boolean deleteContact(String jwt, Long id);

    Resource getContactPicture(Long id);

    Boolean starContact(String jwt, Long id);

    Boolean unstarContact(String jwt, Long id);

    Boolean contactBelongsToUser(String jwt, Long contactId);

    Boolean deleteContactList(String jwt, List<Long> idList);

    String saveContactPicture(MultipartFile file, Long contactId, String jwt);

    Boolean deleteContactPicture(Long contactId, String jwt);
}
