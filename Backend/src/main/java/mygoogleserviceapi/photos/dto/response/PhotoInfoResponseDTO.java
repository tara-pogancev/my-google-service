package mygoogleserviceapi.photos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoInfoResponseDTO {
    private final Long id;
    private final String filename;
}
