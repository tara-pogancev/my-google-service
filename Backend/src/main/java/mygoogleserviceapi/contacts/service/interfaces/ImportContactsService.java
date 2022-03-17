package mygoogleserviceapi.contacts.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportContactsService {

    void importFromCSV(MultipartFile file, String jwt) throws IOException;

    void importFromJSON(MultipartFile file, String jwt);

}
