package mygoogleserviceapi.shared.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfilePictureResponseDTO {
    private String fileName;
    private String fileType;
    private long size;
}
