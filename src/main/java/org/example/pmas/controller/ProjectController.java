package org.example.pmas.controller;

import org.example.pmas.service.ProjectService;

public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService){
        this.projectService=projectService;
    }

}
