package org.example.controllers;


import org.example.models.User;
import org.example.services.AuthService;
import org.example.util.Session;
import org.example.views.MainMenu;

import java.util.Scanner;

public class AuthController {

      private AuthService authService;
      private Session session = Session.getInstance();
      private MainMenu mainMenu = new MainMenu();

      public AuthController(AuthService authService) {
          this.authService = authService;
      }


      Scanner sc = new Scanner(System.in);

      public  void findUser(){


          boolean NoValid = false;
          String email;
          String password;
          do {
          System.out.println("Enter your Email");
           email = sc.next();
          System.out.println("Enter your Password");
           password = sc.next();

          if(!email.contains("@") && email.length()<5){
              System.out.println("Invalid Email");
              NoValid = true;
          }

          if(password.length()<6){
              System.out.println("Invalid Password");
              NoValid = true;
          }

          }while(NoValid);

           User user = this.authService.findUserByEmailAndPassword(email,password);
           if(user!= null){
                   switch (user.getRoleId()){
                       case 1:
                           System.out.println("Admin");
                           break;
                       case 2:
                           System.out.println("TELLER");
                            mainMenu.menuTeller(session.getSession("username"));
                           break;
                       case 3:
                           System.out.println("AUDITOR");
                           break;
                       case 4:
                           System.out.println("Manager");
                           break;
                   }
           }else{
               System.out.println("You have not successfully logged in" );
           }

      }
}
