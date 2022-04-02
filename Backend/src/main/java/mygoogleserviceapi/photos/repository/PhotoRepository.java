package mygoogleserviceapi.photos.repository;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.id = :userId ORDER BY p.creationDate DESC")
    Slice<Photo> getPhotosForUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.email = :email and p.fileName = :fileName")
    Photo getPhotoForUser(@Param("email") String email,
                          @Param("fileName") String fileName);

    @Query("SELECT SUM(p.size) FROM Photo p WHERE p.applicationUser.id = :userId")
    Long getUsedStorageForUserID(@Param("userId") Long userId);

}
