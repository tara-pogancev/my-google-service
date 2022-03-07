package mygoogleserviceapi.shared.service.interfaces;

public interface AuthorizationService {
    String getUsername();
    
    boolean isEmailInJWT(String email);

    boolean isEmailInJWT(Long userId);


}
