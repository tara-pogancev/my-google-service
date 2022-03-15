package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactCSV;
import mygoogleserviceapi.contacts.service.interfaces.ImportContactsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportContactsServiceImpl implements ImportContactsService {

    @Override
    public void importFromCSV(MultipartFile file, String jwt) throws IOException {
        List<ContactCSV> contactsCSV = new ArrayList<>();
        Reader reader = new InputStreamReader(file.getInputStream());
        try (ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
            final String[] headers = beanReader.getHeader(true);
            ContactCSV contactCSV;
            while ((contactCSV = beanReader.read(ContactCSV.class, headers)) != null) {
                contactsCSV.add(contactCSV);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void importFromJSON(MultipartFile file, String jwt) {

    }
}
