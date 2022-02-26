package mygoogleserviceapi.contacts.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class ContactList extends DatabaseEntity {

    @OneToMany(mappedBy = "contactList")
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private ApplicationUser owner;

}
