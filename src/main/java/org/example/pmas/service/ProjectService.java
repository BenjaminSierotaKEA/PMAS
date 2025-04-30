package org.example.pmas.service;

import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.ProjectRepository;

public class ProjectService {

    private final IProjectRepository projectRepository;


    public ProjectService(IProjectRepository projectRepository){
        this.projectRepository=projectRepository;

    }



}
