package mygoogleserviceapi.photos.repository;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.id = :userId" +
            " AND (:favorites IS FALSE OR p.favorite IS TRUE)" +
            " AND (cast(:beforeDate as date) IS NULL OR p.creationDate <= :beforeDate)" +
            " AND (cast(:afterDate as date) IS NULL OR p.creationDate >= :afterDate)" +
            " AND UPPER(p.fileName) LIKE CONCAT('%',UPPER(:searchValue),'%')" +
            " ORDER BY p.creationDate DESC")
    Slice<Photo> getPhotosForUserId(@Param("userId") Long userId,
                                    @Param("favorites") boolean favorites,
                                    @Param("searchValue") String searchValue,
                                    @Param("beforeDate") LocalDateTime beforeDate,
                                    @Param("afterDate") LocalDateTime afterDate,
                                    Pageable pageable);

    @Query("SELECT p FROM Photo p WHERE p.applicationUser.email = :email and p.fileName = :fileName")
    Photo getPhotoForUser(@Param("email") String email,
                          @Param("fileName") String fileName);

    @Query("SELECT SUM(p.size) FROM Photo p WHERE p.applicationUser.id = :userId")
    Long getUsedStorageForUserID(@Param("userId") Long userId);

    @Query("SELECT p from Photo p WHERE p.applicationUser.id = :userId AND ((:fileNames) IS NULL OR p.fileName IN (:fileNames))")
    Set<Photo> getPhotosForExport(
            @Param("userId") Long userId,
            @Param("fileNames") List<String> fileNames);

}
