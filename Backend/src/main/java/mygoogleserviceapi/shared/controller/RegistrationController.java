package mygoogleserviceapi.shared.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.dto.UserDTO;
import mygoogleserviceapi.shared.model.ApplicationUser;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final ApplicationUserService userService;

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody UserDTO newUser) {
        ApplicationUser createdUser = userService.registerNewUser(newUser);
        if (createdUser != null) return new ResponseEntity(createdUser.getFullName(), HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
