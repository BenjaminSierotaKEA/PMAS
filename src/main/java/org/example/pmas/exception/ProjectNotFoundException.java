package org.example.pmas.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(int id) {
        super("Project med ID" + id + " var ikke fundet");
    }
}
