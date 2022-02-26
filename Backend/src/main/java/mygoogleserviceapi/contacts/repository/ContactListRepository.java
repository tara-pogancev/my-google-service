package mygoogleserviceapi.contacts.repository;

import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.shared.repository.EntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactListRepository extends EntityRepository<ContactList> {

    @Query("SELECT c FROM ContactList c WHERE c.owner.id=:userId")
    ContactList getByUserId(@Param("userId") Long userId);

}
