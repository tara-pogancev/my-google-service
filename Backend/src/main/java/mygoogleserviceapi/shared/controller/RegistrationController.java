package mygoogleserviceapi.shared.controller;

import lombok.RequiredArgsConstructor;
import mygoogleserviceapi.shared.service.interfaces.ApplicationUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final ApplicationUserService userService;

}
