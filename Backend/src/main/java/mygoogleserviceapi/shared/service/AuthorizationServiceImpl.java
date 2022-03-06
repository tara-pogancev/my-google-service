package mygoogleserviceapi.shared.service;

import mygoogleserviceapi.shared.service.interfaces.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationServiceImpl implements AuthorizationService {

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private String getUsername() {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    @Override
    public boolean isEmailInJWT(String email) {
        return email.equals(getUsername());
    }
}
