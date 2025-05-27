package org.example.pmas.model;

import org.example.pmas.model.enums.PriorityLevel;

import java.time.LocalDate;
import java.util.Set;

public class Task {
    private int id;
    private final int maxNameLength = 200;
    private final int maxDescriptionLength = 200;
    private String name, description;
    private PriorityLevel priorityLevel;
    private Double timeBudget;
    private boolean completed;
    private LocalDate deadline;
    private SubProject subProject;
    private Set<User> users;

    public Task() {}

    public Task(int id, String name, String description, PriorityLevel priorityLevel, double timeBudget,
                boolean completed, LocalDate deadline, SubProject subProject,
                Set<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.timeBudget = timeBudget;
        this.completed = completed;
        this.deadline = deadline;
        this.subProject = subProject;
        this.users = users;
    }

    public Task(String name, String description, PriorityLevel priorityLevel, double timeBudget,
                boolean completed, LocalDate deadline, SubProject subProject,
                Set<User> users) {
        this.name = name;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.timeBudget = timeBudget;
        this.completed = completed;
        this.deadline = deadline;
        this.subProject = subProject;
        this.users = users;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (!(other instanceof Task)) return false;

        return id == ((Task) other).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
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

    public Double getTimeBudget() {
        return timeBudget;
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
        if(name.length() > maxNameLength){
            this.name = name.substring(0, maxNameLength - 1);
        }else{
            this.name = name;
        }
    }

    public void setDescription(String description) {
        if(description.length() > maxDescriptionLength){
            this.description = description.substring(0, maxDescriptionLength-1);
        }else{
            this.description = description;
        }
    }

    public void setTimeBudget(double timeBudget) {
        this.timeBudget = timeBudget;
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
}
