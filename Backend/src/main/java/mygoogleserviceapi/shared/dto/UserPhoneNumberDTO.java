package mygoogleserviceapi.shared.dto;

import lombok.Data;

@Data
public class UserPhoneNumberDTO {
    public Long id;
    public Long userId;
    public String phoneNumber;
    public String type;

}
