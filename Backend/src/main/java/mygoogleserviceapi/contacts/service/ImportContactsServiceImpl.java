package mygoogleserviceapi.contacts.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.csv.ContactSimpleCSV;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.CreateContactsFromImportService;
import mygoogleserviceapi.contacts.service.interfaces.ImportContactsService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
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


    private final DataConverter converter;
    private final CreateContactsFromImportService createContactsFromImportService;

    @Override
    public void importFromCSV(MultipartFile file, String jwt) throws IOException {
        List<ContactSimpleCSV> contactsCSV = new ArrayList<>();
        Reader reader = new InputStreamReader(file.getInputStream());
        try (ICsvBeanReader beanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
            final String[] headers = beanReader.getHeader(true);
            final CellProcessor[] processors = getProcessors();

            ContactSimpleCSV contactCSV;
            while ((contactCSV = beanReader.read(ContactSimpleCSV.class, headers, processors)) != null) {
                contactsCSV.add(contactCSV);
            }

            List<Contact> contacts = converter.convert(contactsCSV, Contact.class);
            for (Contact contact : contacts) {
                if (!createContactsFromImportService.userHasIdenticalContact(jwt, contact)) {
                    createContactsFromImportService.createFromImportedContact(jwt, contact);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // First name
                new NotNull(), // Last name
                new ParseBool(), // starred
                new ParseBool(), // deleted
                new NotNull(), // email addresses
                new NotNull(), // phone numbers
        };

        return processors;
    }

    @Override
    public void importFromJSON(MultipartFile file, String jwt) {

    }


}
