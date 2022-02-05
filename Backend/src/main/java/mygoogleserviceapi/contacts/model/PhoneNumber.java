package mygoogleserviceapi.contacts.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.contacts.enumeration.ContactTypeEnum;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class PhoneNumber extends DatabaseEntity {

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "type")
    private ContactTypeEnum type;

}
