package mygoogleserviceapi.shared.service.interfaces;

import mygoogleserviceapi.shared.model.ApplicationUser;

public interface ApplicationUserService{

    ApplicationUser findByEmail(String email);

}
