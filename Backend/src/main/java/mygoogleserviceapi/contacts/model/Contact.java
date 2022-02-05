package mygoogleserviceapi.contacts.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Contact extends DatabaseEntity {

    @Column(name = "firstName", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "starred")
    private Boolean starred = false;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "contact")
    private Set<ContactPhoneNumber> phoneNumbers;

    @OneToMany(mappedBy = "contact")
    private Set<ContactEmailAddress> emailAddresses;

    //todo: contact picture

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser contactApplicationUser;

    @ManyToOne
    @JoinColumn(name = "contactList_id", nullable = false)
    private ContactList contactList;

    public String getFullName() {
        return name + " " + lastName;
    }

}
