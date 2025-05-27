package org.example.pmas.model;

import java.util.List;

public class SubProject {
    private int id;
    private final int maxNameLength = 200;
    private String name;
    private String description;
    private final int maxDescriptionLength = 200;
    private Double timeBudget;
    private boolean completed;
    private Project project;
    private int projectID;
    private List<Task> tasks;

    public SubProject(){}

    public SubProject(int id, String name){
        this.id = id;
        this.name = name;
    }

    public SubProject(String name, String description, Double timeBudget, boolean completed, int projectID) {
        this.name = name;
        this.description = description;
        this.timeBudget = timeBudget;
        this.completed = completed;
        this.projectID = projectID;
    }

    public SubProject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public SubProject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubProject(int id) {
        this.id = id;
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
        if(name.length() > maxNameLength){
            this.name = name.substring(0, maxNameLength-1);
        }else{
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.length() > maxDescriptionLength){
            this.description = description.substring(0, maxDescriptionLength-1);
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

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectID() {
        return projectID;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof SubProject)) return false;
        return id == ((SubProject) other).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
