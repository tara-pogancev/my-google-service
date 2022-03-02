package mygoogleserviceapi.photos.model;

import lombok.Getter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Photo extends DatabaseEntity {

    public Photo() {
    }

    public Photo(String fileName, ApplicationUser applicationUser) {
        this.fileName = fileName;
        this.applicationUser = applicationUser;
    }

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;
}
