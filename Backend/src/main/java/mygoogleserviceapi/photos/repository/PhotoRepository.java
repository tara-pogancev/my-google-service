package mygoogleserviceapi.photos.repository;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.id = :userId")
    Set<Photo> getPhotosForUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.email = :email and p.fileName = :fileName")
    Photo getPhotoForUser(@Param("email") String email,
                          @Param("fileName") String fileName);
}
