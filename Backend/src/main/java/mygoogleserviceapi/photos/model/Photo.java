package mygoogleserviceapi.photos.model;

import lombok.Getter;
import lombok.Setter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
public class Photo extends DatabaseEntity {

    public Photo() {
    }

    public Photo(String fileName, ApplicationUser applicationUser, LocalDateTime creationDate) {
        this.fileName = fileName;
        this.applicationUser = applicationUser;
        this.creationDate = creationDate;
    }

    @Column(name = "fileName", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "favorite")
    @Setter
    private boolean favorite = false;
}
