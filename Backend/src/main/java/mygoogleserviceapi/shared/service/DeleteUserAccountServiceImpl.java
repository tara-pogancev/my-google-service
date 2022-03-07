package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.service.interfaces.BinService;
import mygoogleserviceapi.contacts.service.interfaces.ContactAppUserService;
import mygoogleserviceapi.photos.service.interfaces.PhotoService;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.ApplicationUserRepository;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.DeleteUserAccountService;
import mygoogleserviceapi.shared.service.interfaces.FileStorageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserAccountServiceImpl implements DeleteUserAccountService {

    private final BinService binService;
    private final ApplicationUserService userService;
    private final FileStorageService fileStorageService;
    private final ApplicationUserRepository userRepository;
    private final ContactAppUserService contactAppUserService;
    private final PhotoService photoService;

    @Override
    public Boolean deleteUserAccount(String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (user != null) {
            this.fileStorageService.deleteProfilePicture(user.getId());
            this.binService.deleteContactListByUser(jwt);
            this.photoService.deleteAllPhotos(user.getEmail());
            String deletedEmail = user.getEmail();
            contactAppUserService.removeAppUserFromContactsByEmail(deletedEmail);
            userRepository.delete(user);
            contactAppUserService.refreshContactAppUserByEmail(deletedEmail);
            return true;
        } else {
            return false;
        }
    }


}
