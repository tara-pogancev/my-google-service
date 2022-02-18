package mygoogleserviceapi.shared.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public String defaultApplication;
    public List<UserPhoneNumberDTO> phoneNumbers;

}
