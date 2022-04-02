package mygoogleserviceapi.photos.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhotoStorageInfoResponseDTO {
    private final Long storageCapacity;
    private final Long usedStorage;
}
