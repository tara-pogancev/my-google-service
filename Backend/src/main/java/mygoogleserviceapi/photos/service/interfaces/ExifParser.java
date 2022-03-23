package mygoogleserviceapi.photos.service.interfaces;

import java.io.File;

public interface ExifParser {
    Double getLat(File image);

    Double getLong(File image);

    void rotate(File image);


    void setLat(File image, Double latitude);

    void setLong(File image, Double longitude);
}
