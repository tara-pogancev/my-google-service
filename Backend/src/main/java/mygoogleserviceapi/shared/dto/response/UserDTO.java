package mygoogleserviceapi.shared.dto.response;

import lombok.Data;

@Data
public class UserDTO {
    public String firstName;
    public String lastName;
    public String password;
    public String email;

}
