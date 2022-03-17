package mygoogleserviceapi.contacts.csv;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ContactSimpleCSV {

    /***
     * Helper class used to parse CSV data
     */

    public String firstName;
    public String lastName;
    public Boolean starred;
    public Boolean deleted;
    public String emails;
    public String phoneNumbers;

    public List<String> getEmailAddresses() {
        List<String> emails = new ArrayList<>();
        String emailList = this.emails.substring(1, this.emails.length() - 1);
        if (!emailList.isEmpty()) {
            emails = new ArrayList<>(Arrays.asList(emailList.split(", ")));
        }
        return emails;
    }

    public List<String> getPhoneNumbers() {
        List<String> phoneNumbers = new ArrayList<>();
        String phoneNumberList = this.phoneNumbers.substring(1, this.phoneNumbers.length() - 1);
        if (!phoneNumberList.isEmpty()) {
            phoneNumbers = new ArrayList<>(Arrays.asList(phoneNumberList.split(", ")));
        }
        return phoneNumbers;
    }

}
