package mygoogleserviceapi.photos.service;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.core.StandardFormat;
import com.thebuzzmedia.exiftool.core.StandardOptions;
import com.thebuzzmedia.exiftool.core.StandardTag;
import com.thebuzzmedia.exiftool.exceptions.UnsupportedFeatureException;
import mygoogleserviceapi.photos.exception.ImageMetadataIOException;
import mygoogleserviceapi.photos.service.interfaces.ExifParser;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

@Service
public class ExifParserImpl implements ExifParser {

    private ExifTool exifTool;

    // mapping rotation cycles to exif CW rotation values
    private final HashMap<Integer, String> rotationValueMapper = new HashMap<Integer, String>() {{
        put(1, "1");
        put(2, "6");
        put(3, "3");
        put(4, "8");
    }};
    private final HashMap<String, Integer> inverseRotationValueMapper = new HashMap<String, Integer>() {{
        put("1", 1);
        put("6", 2);
        put("3", 3);
        put("8", 4);
    }};
    private final int NUM_OF_ROTATIONS = 4;


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
            if (latString == null) {
                return null;
            }
            return Double.valueOf(latString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Double getLong(File image) {
        try {
            String latString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.GPS_LONGITUDE)).get(StandardTag.GPS_LONGITUDE);
            if (latString == null) {
                return null;
            }
            return Double.valueOf(latString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setLat(File image, Double latitude) {
        try {
            exifTool.setImageMeta(image,
                    StandardOptions.builder().withFormat(StandardFormat.NUMERIC).withOverwiteOriginal().build(),
                    Collections.singletonMap(StandardTag.GPS_LATITUDE, latitude.toString()));
        } catch (IOException e) {
            throw new ImageMetadataIOException();
        }
    }

    @Override
    public void setLong(File image, Double longitude) {
        try {
            exifTool.setImageMeta(image,
                    StandardOptions.builder().withFormat(StandardFormat.NUMERIC).withOverwiteOriginal().build(),
                    Collections.singletonMap(StandardTag.GPS_LONGITUDE, longitude.toString()));
        } catch (IOException e) {
            throw new ImageMetadataIOException();
        }
    }

    @Override
    public void rotate(File image) {
        try {
            String rotString = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.ORIENTATION)).get(StandardTag.ORIENTATION);
            int rot = 2;
            if (rotString != null) {
                rot = inverseRotationValueMapper.get(rotString);
                rot = (rot % NUM_OF_ROTATIONS) + 1;
            }
            exifTool.setImageMeta(image,
                    StandardOptions.builder().withFormat(StandardFormat.NUMERIC).withOverwiteOriginal().build(),
                    Collections.singletonMap(StandardTag.ORIENTATION, rotationValueMapper.get(rot)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
