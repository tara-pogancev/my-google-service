package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.service.interfaces.BinService;
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

    @Override
    public Boolean deleteUserAccount(String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (user != null) {
            this.fileStorageService.deleteProfilePicture(user.getId());
            this.binService.deleteContactListByUser(jwt);
            //TODO: Delete images

            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }


}
