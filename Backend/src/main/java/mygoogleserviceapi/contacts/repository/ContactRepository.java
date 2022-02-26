package mygoogleserviceapi.contacts.repository;

import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.shared.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends EntityRepository<Contact> {

}
