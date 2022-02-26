package mygoogleserviceapi.contacts.repository;

import mygoogleserviceapi.contacts.model.ContactEmailAddress;
import mygoogleserviceapi.shared.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactEmailAddressRepository extends EntityRepository<ContactEmailAddress> {
}
