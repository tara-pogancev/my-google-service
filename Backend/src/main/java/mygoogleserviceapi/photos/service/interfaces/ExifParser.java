package mygoogleserviceapi.photos.service.interfaces;

import java.io.File;

public interface ExifParser {
    Double getLat(File image);

    Double getLong(File image);

}
