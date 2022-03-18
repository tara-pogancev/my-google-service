package mygoogleserviceapi.contacts.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Contact extends DatabaseEntity {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "starred")
    private Boolean starred = false;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.REMOVE)
    private Set<ContactEmailAddress> emailAddresses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser contactApplicationUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactList_id", nullable = false)
    private ContactList contactList;

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

    public Boolean hasEmailAddress(String email) {
        for (ContactEmailAddress emailAddress : this.getEmailAddresses()) {
            if (emailAddress.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Requires matching name, matching number of emails and phone numbers,
     * and matching email and phone number strings. Other parameters are not
     * included.
     *
     * @param contact
     * @return
     */
    public Boolean isContactMatching(Contact contact) {
        if (!this.getFullName().equals(contact.getFullName())) {
            return false;
        }

        if (this.getEmailAddresses().size() != contact.getEmailAddresses().size()
                || this.getPhoneNumbers().size() != contact.getPhoneNumbers().size()) {
            return false;
        }

        for (ContactEmailAddress emailAddress : this.getEmailAddresses()) {
            Boolean matchFound = false;

            for (ContactEmailAddress emailAddress1 : contact.getEmailAddresses()) {
                if (emailAddress.getEmail().equals(emailAddress1.getEmail())) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                return false;
            }
        }

        for (ContactPhoneNumber phoneNumber : this.getPhoneNumbers()) {
            Boolean matchFound = false;

            for (ContactPhoneNumber phoneNumber1 : contact.getPhoneNumbers()) {
                if (phoneNumber.getPhoneNumber().equals(phoneNumber1.getPhoneNumber())) {
                    matchFound = true;
                    break;
                }
            }

            if (!matchFound) {
                return false;
            }
        }

        return true;
    }

    public Boolean hasMatchingEmail(String email) {
        for (ContactEmailAddress emailAddress : getEmailAddresses()) {
            if (emailAddress.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public Boolean hasMatchingPhoneNumber(String phoneNumber) {
        for (ContactPhoneNumber contactPhoneNumber : getPhoneNumbers()) {
            if (contactPhoneNumber.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
}
