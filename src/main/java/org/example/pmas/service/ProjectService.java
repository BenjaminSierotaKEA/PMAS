package org.example.pmas.service;

import org.example.pmas.repository.ProjectRepository;

public class ProjectService {

    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository){
        this.projectRepository=projectRepository;

    }



}
