package mygoogleserviceapi.shared.converter;

import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToDTO implements Converter<ApplicationUser, UserDTO> {

    @Override
    public UserDTO convert(ApplicationUser source) {
        UserDTO dto = new UserDTO();
        dto.setEmail(source.getEmail());
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setId(source.getId());
        return dto;
    }

}
