package mygoogleserviceapi.photos.dto.request;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UserInfoRequestDTO {
    public UserInfoRequestDTO() {
    }

    @Email
    private String email;
}
