package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.contacts.repository.ContactRepository;
import mygoogleserviceapi.contacts.service.interfaces.BinService;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.ContactPictureStorageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinServiceImpl implements BinService {

    private final ContactListService contactListService;
    private final ContactRepository contactRepository;
    private final ContactPictureStorageService storageService;

    @Override
    public List<Contact> getDeletedContacts(String jwt) {
        ContactList contactList = contactListService.getContactList(jwt);
        if (contactList != null) {
            List<Contact> contactsArrayList = contactList.getContacts();
            return contactsArrayList.stream().filter(c -> c.getDeleted()).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Boolean deleteAllContactsInBin(String jwt) {
        List<Contact> deletedContacts = getDeletedContacts(jwt);
        if (deletedContacts != null) {
            for (Contact contact : deletedContacts) {
                storageService.deleteContactPicture(contact.getId());
                contactRepository.delete(contact);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteContact(String jwt, Long id) {
        Contact contact = contactRepository.getById(id);
        if (contact != null && contactListService.contactBelongsToUser(jwt, id)) {
            storageService.deleteContactPicture(contact.getId());
            contactRepository.delete(contact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteContactList(List<Long> idList, String jwt) {
        for (Long id : idList) {
            Contact contact = contactRepository.getById(id);
            if (contactListService.contactBelongsToUser(jwt, id)) {
                storageService.deleteContactPicture(contact.getId());
                contactRepository.delete(contact);
            }
        }
        return true;
    }

    @Override
    public Boolean recoverContactList(List<Long> idList, String jwt) {
        for (Long id : idList) {
            Contact contact = contactRepository.getById(id);
            if (contactListService.contactBelongsToUser(jwt, id)) {
                contact.setDeleted(false);
                contactRepository.save(contact);
            }
        }
        return true;
    }

    @Override
    public Boolean recoverContact(String jwt, Long id) {
        Contact contact = contactRepository.getById(id);
        if (contact != null && contactListService.contactBelongsToUser(jwt, id)) {
            contact.setDeleted(false);
            contactRepository.save(contact);
            return true;
        } else {
            return false;
        }
    }
}
