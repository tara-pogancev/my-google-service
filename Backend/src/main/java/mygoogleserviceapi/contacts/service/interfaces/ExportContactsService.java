package mygoogleserviceapi.contacts.service.interfaces;

import java.io.Writer;
import java.util.List;

public interface ExportContactsService {

    void exportAllContactsByUserToCsv(Writer writer, String jwt);

    void exportSelectedContactsByUserToCsv(Writer writer, String jwt, List<Long> ids);

    void exportAllContactsByUserToJson(Writer writer, String jwt);

    void exportSelectedContactsByUserToJson(Writer writer, String jwt, List<Long> ids);

}
