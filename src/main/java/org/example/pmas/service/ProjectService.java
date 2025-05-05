package org.example.pmas.service;

import org.example.pmas.model.Project;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final IProjectRepository projectRepository;


    public ProjectService(IProjectRepository projectRepository){
        this.projectRepository = projectRepository;

    }

    public void createProject(Project project){
        projectRepository.create(project);
    }

    public List<Project> readAll(){
        return projectRepository.readAll();
    }

    public Project readSelected(int id){
        return projectRepository.readSelected(id);
    }

    public boolean updateProject(Project oldProject, Project newProject){
        return projectRepository.update(oldProject, newProject);
    }

    public boolean deleteProject(int id){
        return projectRepository.delete(id);
    }

    public boolean doesProjectExist(int id){
        return projectRepository.doesProjectExist(id);
    }



}
