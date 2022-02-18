package mygoogleserviceapi.shared.service;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.contacts.enumeration.ContactTypeEnum;
import mygoogleserviceapi.contacts.model.UserPhoneNumber;
import mygoogleserviceapi.shared.dto.UserPhoneNumberDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.repository.UserPhoneNumberRepository;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import mygoogleserviceapi.shared.service.interfaces.UserPhoneNumberService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPhoneNumberServiceImpl implements UserPhoneNumberService {

    private final UserPhoneNumberRepository phoneNumberRepository;
    private final ApplicationUserService userService;

    @Override
    public UserPhoneNumber addPhoneNumber(UserPhoneNumberDTO dto, String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        if (user != null && !isPhoneNumberAlreadyDefined(user, dto.phoneNumber)) {

            UserPhoneNumber phoneNumber = new UserPhoneNumber();
            phoneNumber.setPhoneNumber(dto.phoneNumber);
            phoneNumber.setApplicationUser(user);
            phoneNumber.setType(ContactTypeEnum.getEnumFromString(dto.type));

            phoneNumberRepository.save(phoneNumber);
            return phoneNumber;

        }
        return null;
    }

    @Override
    public Boolean deletePhoneNumber(UserPhoneNumberDTO dto, String jwt) {
        UserPhoneNumber phoneNumber = getById(dto.id);
        if (phoneNumber != null) {
            phoneNumberRepository.delete(phoneNumber);
            return true;
        }
        return false;
    }

    @Override
    public UserPhoneNumber changePhoneNumber(UserPhoneNumberDTO dto, String jwt) {
        ApplicationUser user = userService.getUserByJwt(jwt);
        UserPhoneNumber phoneNumber = getById(dto.id);
        if (user != null && phoneNumber != null) {

            if (!phoneNumber.getPhoneNumber().equals(dto.getPhoneNumber())) {
                if (isPhoneNumberAlreadyDefined(user, dto.phoneNumber)) {
                    return null;
                }
            }

            phoneNumber.setPhoneNumber(dto.phoneNumber);
            phoneNumber.setType(ContactTypeEnum.getEnumFromString(dto.type));

            phoneNumberRepository.save(phoneNumber);
            return phoneNumber;

        }
        return null;
    }

    @Override
    public UserPhoneNumber getById(Long id) {
        return phoneNumberRepository.getById(id);
    }

    private Boolean isPhoneNumberAlreadyDefined(ApplicationUser user, String phoneNumber) {
        for (UserPhoneNumber userPhoneNumber : user.getPhoneNumbers()) {
            if (userPhoneNumber.equals(phoneNumber))
                return true;
        }
        return false;
    }

}
