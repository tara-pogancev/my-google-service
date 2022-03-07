package mygoogleserviceapi.shared.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.contacts.model.UserPhoneNumber;
import mygoogleserviceapi.shared.enumeration.DefaultApplicationEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class ApplicationUser extends DatabaseEntity {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "defaultApplication")
    private DefaultApplicationEnum defaultApplication = DefaultApplicationEnum.NONE;

    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.REMOVE)
    private Set<UserPhoneNumber> phoneNumbers = new HashSet<>();

    public String getFullName() {
        return (firstName + " " + lastName).trim();
    }

}
