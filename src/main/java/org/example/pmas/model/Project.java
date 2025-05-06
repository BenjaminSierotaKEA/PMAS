
package org.example.pmas.model;

import java.time.LocalDate;

public class Project {
    private int id;
    private String name;
    private final int nameMaxLength = 200;
    private String description;
    private final int descriptionMaxLength = 200;
    private int timeBudget;
    private LocalDate deadline;


    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Project(int id, String name, String description, int timeBudget, LocalDate deadline){
        this.id = id;
        //we trim the length of the name and description to the max of what the database allows
        setName(name);
        setDescription(description);
        this.timeBudget = timeBudget;
        this.deadline = deadline;


    }

    public Project(){
        this.id = - 1;
        setName("nothing");
        setDescription("nothing");
        this.timeBudget = -1;
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
        //trims the length of the description to  the max of wha t the database allows
        if(description.length() > descriptionMaxLength){
            this.description = description.substring(0, descriptionMaxLength-1);
        }else{
            this.description = description;
        }
    }

    public int getTimeBudget() {
        return timeBudget;
    }

    public void setTimeBudget(int timeBudget) {
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
                && this.timeBudget == otherProject.timeBudget
                && this.deadline.equals(otherProject.deadline);

    }
}