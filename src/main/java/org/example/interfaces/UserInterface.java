package org.example.interfaces;

import org.example.models.User;

public interface UserInterface {
    public User findUser(String email, String password);
}
