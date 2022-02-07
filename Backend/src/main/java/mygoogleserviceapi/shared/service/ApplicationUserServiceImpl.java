package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.ApplicationUserRepository;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final ApplicationUserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Override
    public ApplicationUser findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public String saveProfilePicture(MultipartFile file, Long userId) {
        return fileStorageService.storeProfilePicture(file, userId);
    }

    @Override
    public Resource getProfilePicture(Long userId) {
        return fileStorageService.loadProfilePicture(userId);
    }


}
