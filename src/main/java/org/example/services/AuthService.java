package org.example.services;

import org.example.models.User;
import org.example.repositories.UserRepository;

public class AuthService {

    private  UserRepository userRepository ;
    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserByEmailAndPassword(String email, String  password){
          User user  = this.userRepository.findUser(email,password);
          if(user != null){
            switch (user.getRoleId()){
                case 1:
                    System.out.println("Admin");
                    break;
                case 2:
                    System.out.println("TELLER");
                    break;
                case 3:
                    System.out.println("AUDITOR");
                    break;
                case 4:
                    System.out.println("Manager");
                    break;
            }
              return user;
          }
            return null;

    }

}
