package mygoogleserviceapi.shared.dto;

import lombok.Data;

@Data
public class UserDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String password;
    public String email;

}
