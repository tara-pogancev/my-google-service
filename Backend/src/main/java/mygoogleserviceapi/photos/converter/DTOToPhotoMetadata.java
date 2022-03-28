package mygoogleserviceapi.photos.converter;

import mygoogleserviceapi.photos.dto.request.PhotoMetadataRequestDTO;
import mygoogleserviceapi.photos.model.PhotoMetadata;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DTOToPhotoMetadata implements Converter<PhotoMetadataRequestDTO, PhotoMetadata> {
    @Override
    public PhotoMetadata convert(PhotoMetadataRequestDTO source) {
        return new PhotoMetadata(source.getLatitude(), source.getLongitude());
    }
}
