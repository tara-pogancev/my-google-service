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
import mygoogleserviceapi.contacts.service.interfaces.ContactPictureStorageService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactListServiceImpl implements ContactListService {

    private final ApplicationUserService userService;
    private final ContactRepository contactRepository;
    private final FileStorageService fileStorageService;
    private final ContactListRepository contactListRepository;
    private final ContactPictureStorageService contactPictureStorageService;
    private final ContactPhoneNumberRepository contactPhoneNumberRepository;
    private final ContactEmailAddressRepository contactEmailAddressRepository;

    @Override
    public ContactList getContactList(String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (user != null) {
            return contactListRepository.getByUserId(user.getId());
        }
        return null;
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
        }
        return null;
    }

    @Override
    public Contact addNewContact(String jwt, ContactDTO dto) {
        //todo: add new contact
        return null;
    }

    @Override
    public Boolean deleteContact(String jwt, Long id) {
        Contact contact = contactRepository.getById(id);
        if (contact != null && contactBelongsToUser(jwt, id)) {
            contact.setStarred(false);
            contact.setDeleted(true);
            contactRepository.save(contact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteContactList(String jwt, List<Long> idList) {
        for (Long id : idList) {
            Contact contact = contactRepository.getById(id);
            if (contactBelongsToUser(jwt, id)) {
                contact.setStarred(false);
                contact.setDeleted(true);
                contactRepository.save(contact);
            }
        }
        return true;
    }

    @Override
    public String saveContactPicture(MultipartFile file, Long contactId, String jwt) {
        if (contactBelongsToUser(jwt, contactId)) {
            return contactPictureStorageService.storeContactPicture(file, contactId);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteContactPicture(Long contactId, String jwt) {
        if (contactBelongsToUser(jwt, contactId)) {
            contactPictureStorageService.deleteContactPicture(contactId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Resource getContactPicture(Long id) {
        Contact contact = contactRepository.getById(id);
        Resource contactPicture = contactPictureStorageService.loadContactPicture(id);
        if (contactPicture == null && contact.getContactApplicationUser() != null) {
            contactPicture = fileStorageService.loadProfilePicture(contact.getContactApplicationUser().getId());
        }
        return contactPicture;
    }

    @Override
    public Boolean starContact(String jwt, Long id) {
        Contact contact = contactRepository.getById(id);
        if (contact != null && contactBelongsToUser(jwt, id)) {
            contact.setStarred(true);
            contactRepository.save(contact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean unstarContact(String jwt, Long id) {
        Contact contact = contactRepository.getById(id);
        if (contact != null && contactBelongsToUser(jwt, id)) {
            contact.setStarred(false);
            contactRepository.save(contact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean contactBelongsToUser(String jwt, Long contactId) {
        Contact contact = contactRepository.getById(contactId);
        ApplicationUser owner = userService.getUserByJwt(jwt);
        return contact.getContactList().getOwner() == owner;
    }

}
