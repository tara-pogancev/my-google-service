package mygoogleserviceapi.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;
    private String name;
    private String email;
    private String defaultApplication;
    private Long id;

}