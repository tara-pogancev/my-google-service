package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.dto.response.UserDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.ApplicationUserRepository;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ApplicationUser findByEmail(String email) {
        if (email.isEmpty())
            return null;

        return userRepository.findUserByEmail(email.toLowerCase().replaceAll("\\s+",""));
    }

    @Override
    public String saveProfilePicture(MultipartFile file, Long userId) {
        return fileStorageService.storeProfilePicture(file, userId);
    }

    @Override
    public Resource getProfilePicture(Long userId) {
        return fileStorageService.loadProfilePicture(userId);
    }

    @Override
    public ApplicationUser registerNewUser(UserDTO newUser) {
        if ((this.findByEmail(newUser.email) == null)) {
            ApplicationUser user = new ApplicationUser();
            user.setFirstName(newUser.firstName);
            user.setLastName(newUser.lastName);
            user.setEmail(newUser.email);
            user.setPassword(new BCryptPasswordEncoder().encode(newUser.password));
            return userRepository.save(user);

        } else return null;
    }

    @Override
    public ApplicationUser getById(Long id) {
        return userRepository.getById(id);
    }


}
