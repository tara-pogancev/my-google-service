package mygoogleserviceapi.photos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddPhotoResponseDTO {
    private final Long id;
    private final String filename;
    private final boolean saved;
}
