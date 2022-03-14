package mygoogleserviceapi.contacts.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.dto.ContactCSV;
import mygoogleserviceapi.contacts.dto.ContactJSON;
import mygoogleserviceapi.contacts.model.Contact;
import mygoogleserviceapi.contacts.service.interfaces.ContactListService;
import mygoogleserviceapi.contacts.service.interfaces.ContactService;
import mygoogleserviceapi.contacts.service.interfaces.ExportContactsService;
import mygoogleserviceapi.shared.converter.DataConverter;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportContactsServiceImpl implements ExportContactsService {

    private final DataConverter converter;
    private final ContactListService contactListService;
    private final ContactService contactService;


    @Override
    public void exportAllContactsByUserToCsv(Writer writer, String jwt) {
        List<Contact> contacts = contactListService.getContacts(jwt);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"First name", "Last name", "Starred", "Deleted", "Email addresses", "Phone numbers"};
            String[] nameMapping = {"firstName", "lastName", "starred", "deleted", "emails", "phoneNumbers"};
            csvWriter.writeHeader(csvHeader);

            for (Contact contact : contacts) {
                csvWriter.write(converter.convert(contact, ContactCSV.class), nameMapping);
            }

        } catch (IOException e) {
            System.out.println("Error While writing CSV");
        }
    }

    @Override
    public void exportSelectedContactsByUserToCsv(Writer writer, String jwt, List<Long> ids) {
        List<Contact> contacts = contactService.getContactListByIdsByUser(jwt, ids);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE)) {
            String[] csvHeader = {"First name", "Last name", "Starred", "Deleted", "Email addresses", "Phone numbers"};
            String[] nameMapping = {"firstName", "lastName", "starred", "deleted", "emails", "phoneNumbers"};
            csvWriter.writeHeader(csvHeader);

            for (Contact contact : contacts) {
                csvWriter.write(converter.convert(contact, ContactCSV.class), nameMapping);
            }

        } catch (IOException e) {
            System.out.println("Error While writing CSV");
        }
    }

    @Override
    public void exportAllContactsByUserToJson(Writer writer, String jwt) {
        List<Contact> contacts = contactListService.getContacts(jwt);
        Gson gson = new Gson();
        String contactsInJson = gson.toJson(converter.convert(contacts, ContactJSON.class));
        try {
            writer.write(contactsInJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportSelectedContactsByUserToJson(Writer writer, String jwt, List<Long> ids) {
        List<Contact> contacts = contactService.getContactListByIdsByUser(jwt, ids);
        Gson gson = new Gson();
        String contactsInJson = gson.toJson(converter.convert(contacts, ContactJSON.class));
        try {
            writer.write(contactsInJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
