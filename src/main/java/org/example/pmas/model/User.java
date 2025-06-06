package org.example.pmas.model;

import java.util.List;


public class User {

    private int userID;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String picture;
    private List<Task> tasks;
    private List<Project> projects;


    public User(int userID, String name, String email, String password, Role role, String picture) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.role = role;
        this.picture = picture;
    }

    public User(String name, String email, String password, Role role, String picture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.picture = picture;
    }

    public User() {
    }

    public User(int userID) {
        this.userID = userID;
    }

    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public User(int userID, String name, Role role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (!(other instanceof User)) return false;

        return userID == ((User) other).userID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(userID);
    }

    public User(int userID, String name, String email, String password, String picture, Role role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
    }


    //-------------getters/setters-----------------
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Role getRole() {
        if (role == null) {
            role = new Role();
        }
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }



    @Override
    public String toString() {
        return "userID" + " " + userID + " " + "name" + name;
    }

}
