package mygoogleserviceapi.contacts.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImportContactsService {

    public void importFromCSV(MultipartFile file, String jwt) throws IOException;

    public void importFromJSON(MultipartFile file, String jwt);

}
