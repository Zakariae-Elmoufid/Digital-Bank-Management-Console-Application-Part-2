package org.example;

import org.example.config.DatabaseConnection;
import org.example.controllers.AuthController;
import org.example.repositories.UserRepository;
import org.example.services.AuthService;
import org.example.views.MenuAuth;

import java.sql.Connection;
import java.sql.SQLOutput;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        UserRepository userRepository = new UserRepository();
//        AuthService authService = new AuthService(userRepository);
//        AuthController authController = new AuthController(authService);

        MenuAuth  menu = new MenuAuth();
        menu.showMenu();
    }
}