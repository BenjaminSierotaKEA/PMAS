package org.example.pmas.model;

import java.util.List;

public class SubProject {
    private int id;
    private String name;
    private String description;
    private double timeBudget;
    private double timeTaken;
    private boolean completed;
    private Project project;
    private List<Task> tasks;

    public SubProject(){}

    public SubProject(int id, String name){
        this.id = id;
        this.name = name;
    }

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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(double timeBudget) {
        this.timeBudget = timeBudget;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(double timeTaken) {
        this.timeTaken = timeTaken;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
