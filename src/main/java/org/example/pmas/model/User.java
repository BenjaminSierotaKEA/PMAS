package org.example.pmas.model;

import org.example.pmas.repository.Role;

import java.util.List;
import java.util.Set;

public class User {

    private int userID;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String picture;
    private List<Task> tasks;
    private Set<Project> projects;

    public User(int userID, String name, String email, String password, Role role, String picture){
        this.userID=userID;
        this.name=name;
        this.email=email;
        this.role = role;
        this.picture = picture;
    }


    public int getUserID() {
        return userID;
    }


    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
