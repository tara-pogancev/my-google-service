package mygoogleserviceapi.photos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.model.DatabaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
public class Photo extends DatabaseEntity {

    public Photo() {
    }


    @Column(name = "fileName", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    @Column(name = "creationDate", nullable = false)
    private LocalDateTime creationDate;
}
