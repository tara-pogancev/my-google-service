package mygoogleserviceapi.contacts.csv;

import lombok.Data;

import java.util.List;

@Data
public class ContactCSV {
    public String firstName;
    public String lastName;
    public Boolean starred;
    public Boolean deleted;
    public List<String> emails;
    public List<String> phoneNumbers;
}
