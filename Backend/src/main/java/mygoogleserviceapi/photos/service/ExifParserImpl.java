package mygoogleserviceapi.photos.service;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.core.StandardTag;
import com.thebuzzmedia.exiftool.exceptions.UnsupportedFeatureException;
import mygoogleserviceapi.photos.service.interfaces.ExifParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
public class ExifParserImpl implements ExifParser {

    private static ExifTool exifTool;


    @PostConstruct
    public void init() {
        // ExifTool path must be defined as a system property (`exiftool.path`),
        if (System.getProperty("exiftool.path") == null) {
            System.setProperty("exiftool.path", System.getenv("EXIFTOOL_PATH"));
        }
        try {
            exifTool = new ExifToolBuilder().enableStayOpen().build();
        } catch (UnsupportedFeatureException ex) {
            // Fallback to simple exiftool instance.
            exifTool = new ExifToolBuilder().build();
        }
    }

    @Override
    public Double getLat(File image) {
        try {
            String latString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.GPS_LATITUDE)).get(StandardTag.GPS_LATITUDE);
            if (latString != null) {
                return Double.valueOf(latString);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getLong(File image) {
        try {
            String latString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.GPS_LONGITUDE)).get(StandardTag.GPS_LONGITUDE);
            if (latString != null) {
                return Double.valueOf(latString);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
