package mygoogleserviceapi.photos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PhotoInfoResponseDTO {
    private final Long id;
    private final String filename;
    private final Double latitude;
    private final Double longtitude;
    private final LocalDateTime creationDate;
    private final boolean favorite;
    private final long size;
}
