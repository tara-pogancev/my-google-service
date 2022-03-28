package mygoogleserviceapi.photos.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PhotoMetadataRequestDTO {
    @NotNull
    @Range(min = -90, max = 90)
    private Double latitude;
    @NotNull
    @Range(min = -180, max = 180)
    private Double longitude;
}
