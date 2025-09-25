package org.example.models;


import org.example.enums.RoleType;

public class Role {
     private int id;
     private RoleType title;

    public Role(RoleType title, int id) {
        this.title = title;
        this.id = id;
    }
}


