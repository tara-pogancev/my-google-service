package mygoogleserviceapi.shared.service.interfaces;

import mygoogleserviceapi.shared.dto.ChangePasswordDTO;
import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationUserService {

    ApplicationUser findByEmail(String email);

    String saveProfilePicture(MultipartFile file, Long userId);

    Resource getProfilePicture(Long userId);

    ApplicationUser registerNewUser(UserDTO newUser);

    ApplicationUser getById(Long id);

    ApplicationUser changeName(UserDTO dto, String jwt);

    ApplicationUser getUserByJwt(String jwt);

    ApplicationUser changePassword(ChangePasswordDTO dto, String jwt) throws Exception;
}
