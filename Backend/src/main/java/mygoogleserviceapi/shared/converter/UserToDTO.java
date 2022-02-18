package mygoogleserviceapi.shared.converter;

import mygoogleserviceapi.contacts.model.UserPhoneNumber;
import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.dto.UserPhoneNumberDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToDTO implements Converter<ApplicationUser, UserDTO> {

    @Override
    public UserDTO convert(ApplicationUser source) {
        UserDTO dto = new UserDTO();
        dto.setEmail(source.getEmail());
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setId(source.getId());
        dto.setPhoneNumbers(getPhoneNumbers(source));
        dto.setDefaultApplication(source.getDefaultApplication().toString());
        return dto;
    }

    private List<UserPhoneNumberDTO> getPhoneNumbers(ApplicationUser user) {
        List<UserPhoneNumberDTO> list = new ArrayList<>();
        for (UserPhoneNumber phoneNumber : user.getPhoneNumbers()) {
            UserPhoneNumberDTO dto = new UserPhoneNumberDTO();
            dto.setPhoneNumber(phoneNumber.getPhoneNumber());
            dto.setType(phoneNumber.getType().getString());
            dto.setId(phoneNumber.getId());
            dto.setUserId(user.getId());
            list.add(dto);
        }

        return list;
    }

}
