package org.example.pmas.dto;

public class SubProjectDTO {
        private int id;
        private String name;
        private String description;
        private double timeBudget;
        private double timeTaken;
        private boolean completed;
        private int projectID;
        private int totalTasks;
        private int completedTasks;
        private double completionPercentage;

    public SubProjectDTO(int i, String s) {
        this.id = i;
        this.name = s;
    }

    public SubProjectDTO() {

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

    public double getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(double timeBudget) {
        this.timeBudget = timeBudget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public void calculateCompletionPercentage() {
        if (totalTasks == 0) {
            this.completionPercentage = 0;
        } else {
            this.completionPercentage = (completedTasks * 100.0) / totalTasks;
        }
    }

    public void checkIfCompleted() {
        if(timeTaken == timeBudget) {
            this.completed = true;
        }
    }

}
