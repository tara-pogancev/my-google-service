package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.model.ContactList;
import mygoogleserviceapi.contacts.repository.ContactListRepository;
import mygoogleserviceapi.security.JwtUtil;
import mygoogleserviceapi.shared.dto.ChangePasswordDTO;
import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.enumeration.DefaultApplicationEnum;
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

    private final JwtUtil jwtUtil;
    private final FileStorageService fileStorageService;
    private final ApplicationUserRepository userRepository;
    private final ContactListRepository contactListRepository;

    @Override
    public ApplicationUser findByEmail(String email) {
        if (email.isEmpty())
            return null;

        return userRepository.findUserByEmail(email.toLowerCase().replaceAll("\\s+", ""));
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
            ApplicationUser createdUser = userRepository.save(user);
            ContactList contactList = new ContactList();
            contactList.setOwner(createdUser);
            contactListRepository.save(contactList);
            return createdUser;
        } else return null;
    }

    @Override
    public ApplicationUser getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public ApplicationUser changeName(UserDTO dto, String jwt) {
        ApplicationUser user = getUserByJwt(jwt);
        if (user != null) {
            user.setFirstName(dto.firstName);
            user.setLastName(dto.lastName);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public ApplicationUser getUserByJwt(String jwt) {
        jwt = jwt.substring(7);
        String email = jwtUtil.extractUsername(jwt);
        return findByEmail(email);
    }

    @Override
    public ApplicationUser changePassword(ChangePasswordDTO dto, String jwt) throws Exception {
        ApplicationUser user = getUserByJwt(jwt);
        if (user != null) {
            dto.password = new BCryptPasswordEncoder().encode(dto.password);
            user.setPassword(dto.password);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public ApplicationUser setDefaultApplication(String app, String jwt) {
        ApplicationUser user = getUserByJwt(jwt);
        if (user != null) {
            user.setDefaultApplication(DefaultApplicationEnum.getEnumFromString(app));
            userRepository.save(user);

        }
        return user;
    }

    @Override
    public Boolean deleteUserProfilePicture(String jwt) {
        ApplicationUser user = getUserByJwt(jwt);
        if (user != null) {
            this.fileStorageService.deleteProfilePicture(user.getId());
            return true;
        } else {
            return false;
        }
    }


}
