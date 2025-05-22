package org.example.pmas.model.dto;

import java.time.LocalDate;

public class ProjectDTO {
    private int id;
    private String name;
    private String description;
    private double timeBudget;
    private double timeTaken;
    private boolean completed;
    private LocalDate deadline;
    private int projectID;
    private int totalSubProjects;
    private int completedSubProjects;
    private double completionPercentage;

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public ProjectDTO() {

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

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getTotalSubProjects() {
        return totalSubProjects;
    }

    public void setTotalSubProjects(int totalSubProjects) {
        this.totalSubProjects = totalSubProjects;
    }

    public int getCompletedSubProjects() {
        return completedSubProjects;
    }

    public void setCompletedSubProjects(int completedSubProjects) {
        this.completedSubProjects = completedSubProjects;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
