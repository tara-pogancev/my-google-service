package mygoogleserviceapi.photos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PhotoResponseDTO {
    private MultipartFile photo;
    private String fileName;
}
