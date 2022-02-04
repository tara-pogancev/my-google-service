package mygoogleserviceapi.shared.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.enumeration.DefaultApplicationEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.swing.table.DefaultTableCellRenderer;

@Getter
@Setter
@Entity
public class ApplicationUser extends DatabaseEntity {

    @Column(name = "firstName", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "defaultApplication")
    private DefaultApplicationEnum defaultApplication = DefaultApplicationEnum.NONE;

    //Todo: Profile picture

    //Todo: list of phone numbers

}
