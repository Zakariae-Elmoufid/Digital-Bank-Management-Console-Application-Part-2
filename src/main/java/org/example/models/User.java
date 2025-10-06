package org.example.models;

import org.example.enums.RoleType;

public class User {

    private int id;
    private String  username;
    private String  password;
    private String  email;
    private boolean  isActive;
    private int roleId ;

    public User(int id, String username, String email,String password,  boolean isActive, int roleId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

public int  getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {}

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", roleId=" + roleId +
                '}';
    }
}


