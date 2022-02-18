package mygoogleserviceapi.shared.service.interfaces;


import mygoogleserviceapi.contacts.model.UserPhoneNumber;
import mygoogleserviceapi.shared.dto.UserPhoneNumberDTO;

public interface UserPhoneNumberService {

    UserPhoneNumber addPhoneNumber(UserPhoneNumberDTO dto, String jwt);

    Boolean deletePhoneNumber(UserPhoneNumberDTO dto, String jwt);

    UserPhoneNumber changePhoneNumber(UserPhoneNumberDTO dto, String jwt);

    UserPhoneNumber getById(Long id);
}
