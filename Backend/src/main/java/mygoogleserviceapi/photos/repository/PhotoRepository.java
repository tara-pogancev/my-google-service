package mygoogleserviceapi.photos.repository;

import mygoogleserviceapi.photos.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
