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

    public Photo(String fileName, ApplicationUser applicationUser, LocalDateTime creationDate, long size) {
        this.fileName = fileName;
        this.applicationUser = applicationUser;
        this.creationDate = creationDate;
        this.size = size;
        this.latitude = 0D;
        this.longitude = 0D;
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

    @Column(name = "size")
    private long size;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
