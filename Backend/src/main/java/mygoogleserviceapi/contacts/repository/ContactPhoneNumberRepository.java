package mygoogleserviceapi.contacts.repository;

import mygoogleserviceapi.contacts.model.ContactPhoneNumber;
import mygoogleserviceapi.shared.repository.EntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPhoneNumberRepository extends EntityRepository<ContactPhoneNumber> {

}
