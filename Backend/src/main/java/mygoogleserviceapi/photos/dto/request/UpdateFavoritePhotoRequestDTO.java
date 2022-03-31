package mygoogleserviceapi.photos.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UpdateFavoritePhotoRequestDTO {
    @NotNull
    private Boolean favorite;
}
