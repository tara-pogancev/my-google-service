package mygoogleserviceapi.shared.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    public String password;
    public String oldPassword;
}
