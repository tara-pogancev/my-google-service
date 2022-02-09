package mygoogleserviceapi.shared.service.interfaces;

import mygoogleserviceapi.shared.dto.response.UserDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationUserService {

    ApplicationUser findByEmail(String email);

    String saveProfilePicture(MultipartFile file, Long userId);

    Resource getProfilePicture(Long userId);

    ApplicationUser registerNewUser(UserDTO newUser);
}
