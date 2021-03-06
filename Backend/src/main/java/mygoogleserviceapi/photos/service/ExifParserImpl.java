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
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

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
        Optional<String> pathOptional = getExifToolPath();
        if (!pathOptional.isPresent()) {
            return;
        }
        String path = pathOptional.get();
        // ExifTool path must be defined as a system property (`exiftool.path`),
        /*
        if (System.getProperty("exiftool.path") == null && System.getenv("exiftool") != null) {
            System.setProperty("exiftool.path", System.getenv("exiftool"));
        }
        if (System.getProperty("exiftool.path") == null)
            return;
         */
        try {
            exifTool = new ExifToolBuilder().withPath(path).enableStayOpen().build();
        } catch (UnsupportedFeatureException ex) {
            // Fallback to simple exiftool instance.
            exifTool = new ExifToolBuilder().withPath(path).build();
        }
    }

    private Optional<String> getExifToolPath() {
        String[] paths = System.getenv("PATH").split(File.pathSeparator);
        for (String path : paths) {
            File dir = new File(path);
            if (!dir.isDirectory())
                continue;
            Optional<String> fullPath = Arrays.stream(dir.list((dir1, name) -> name.startsWith("exiftool"))).findFirst();
            if (fullPath.isPresent()) {
                return fullPath;
            }
        }
        return Optional.empty();
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

    @Override
    public LocalDateTime getCreationDate(File image) {
        try {
            String date = exifTool.getImageMeta(image, Collections.singletonList(StandardTag.CREATE_DATE)).get(StandardTag.CREATE_DATE);
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
                return LocalDateTime.parse(date, formatter);
            } else {
                BasicFileAttributes attr = Files.readAttributes(image.toPath(), BasicFileAttributes.class);
                FileTime fileTime = attr.creationTime();
                date = fileTime.toString();
                date = date.split("\\.")[0];
                return LocalDateTime.parse(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDateTime.now();
        }
    }


}
