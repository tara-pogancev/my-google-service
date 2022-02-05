package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.ApplicationUserRepository;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    @Override
    public ApplicationUser findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

}
