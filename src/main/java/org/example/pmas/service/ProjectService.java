package org.example.pmas.service;

import org.example.pmas.model.Project;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final IProjectRepository projectRepository;


    public ProjectService(IProjectRepository projectRepository){
        this.projectRepository = projectRepository;

    }

    public void createProject(Project project){
        projectRepository.create(project);
    }



}
