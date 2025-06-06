
package org.example.pmas.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Project {
    private int id;
    private String name;
    private String description;
    private Double timeBudget;
    private LocalDate deadline;
    private boolean completed;
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Project(int id, String name, String description, double timeBudget, LocalDate deadline){
        this.id = id;
        //we trim the length of the name and description to the max of what the database allows
        setName(name);
        setDescription(description);
        this.timeBudget = timeBudget;
        this.deadline = deadline;
    }

    public Project(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        final int nameMaxLength = 200;
        //trims the length of the name to the max of what the database allows
        if(name.length() > nameMaxLength){
            this.name = name.substring(0, nameMaxLength-1);
        }else{
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        final int descriptionMaxLength = 200;
        //trims the length of the description to  the max of wha t the database allows
        if(description.length() > descriptionMaxLength){
            this.description = description.substring(0, descriptionMaxLength-1);
        }else{
            this.description = description;
        }
    }

    public Double getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(double timeBudget) {
        this.timeBudget = timeBudget;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Project)) {
            return false;
        }
        Project otherProject = (Project) other;
        return this.getId() == otherProject.getId()
                && this.getName().equals(otherProject.getName())
                && this.getDescription().equals(otherProject.getDescription())
                && Objects.equals(this.timeBudget, otherProject.timeBudget)
                && this.deadline.equals(otherProject.deadline);

    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}