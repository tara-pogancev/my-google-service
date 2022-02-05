package mygoogleserviceapi.contacts.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.model.ApplicationUser;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class UserPhoneNumber extends PhoneNumber {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ApplicationUser applicationUser;

}
