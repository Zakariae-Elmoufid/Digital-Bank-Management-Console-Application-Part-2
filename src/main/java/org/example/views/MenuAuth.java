package org.example.views;

import org.example.controllers.AuthController;
import org.example.repositories.UserRepository;
import org.example.services.AuthService;

public class MenuAuth {

    public void showMenu(){

        AuthController authController = new AuthController(new AuthService(new UserRepository()));
        authController.findUser();
    }
}
