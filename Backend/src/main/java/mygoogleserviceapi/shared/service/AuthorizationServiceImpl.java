package mygoogleserviceapi.shared.service;

import lombok.AllArgsConstructor;
import mygoogleserviceapi.shared.exception.NotAllowedException;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.AuthorizationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final ApplicationUserService applicationUserService;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    @Override
    public boolean isEmailInJWT(String email) {
        return email.equals(getUsername());
    }

    @Override
    public boolean isEmailInJWT(Long userId) {
        String email = applicationUserService.getById(userId).getEmail();
        return isEmailInJWT(email);
    }

    @Override
    public void isUserAllowedToAccessResource(String userEmail) {
        if (!isEmailInJWT(userEmail)) {
            throw new NotAllowedException();
        }
    }

    @Override
    public void isUserAllowedToAccessResource(Long userId) {
        ApplicationUser user = applicationUserService.getById(userId);
        isUserAllowedToAccessResource(user.getEmail());
    }
}
