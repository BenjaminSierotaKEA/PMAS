package org.example.pmas.model;

import java.util.Set;

public class Task {
    private int id;
    private String name;
    private String description;
    private double timeBudget;
    private double timeTaken;
    private boolean completed;
    private SubProject subProject;
    private Set<User> users;

    public Task() {}

    public Task(int id, String name, String description, double timeBudget,
                double timeTaken, boolean completed, SubProject subProject,
                Set<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.timeBudget = timeBudget;
        this.timeTaken = timeTaken;
        this.completed = completed;
        this.subProject = subProject;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getTimeBudget() {
        return timeBudget;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public boolean isCompleted() {
        return completed;
    }

    public SubProject getSubProject() {
        return subProject;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeBudget(double timeBudget) {
        this.timeBudget = timeBudget;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if(!(other instanceof Task)) return false;

        return id == ((Task) other).id;
    }
}
