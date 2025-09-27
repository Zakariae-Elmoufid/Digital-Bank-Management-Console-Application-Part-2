package org.example.services;

import org.example.models.Account;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.util.Session;

import java.util.List;

public class AuthService {

    private  UserRepository userRepository ;
    private Session session = Session.getInstance();

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUserByEmailAndPassword(String email, String  password){
          User user  = this.userRepository.findUser(email,password);
          if(user!=null){
              session.setSession("email",user.getEmail());
              session.setSession("id",user.getId());
              session.setSession("password",user.getPassword());
              session.setSession("username",user.getUsername());
              session.setSession("role_id", user.getRoleId());
            return user;
          }
            return null;
    }





}
