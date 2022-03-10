package mygoogleserviceapi.contacts.service.interfaces;

import java.io.Writer;
import java.util.List;

public interface ExportContactsService {

    void exportAllContactsByUserToCsv(Writer writer, String jwt);

    void exportSelectedContactsByUserToCsv(Writer writer, String jwt, List<Long> ids);

}
