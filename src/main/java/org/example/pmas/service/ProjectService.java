package org.example.pmas.service;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final IProjectRepository projectRepository;
    private final ISubProjectRepository subprojectRepository;


    public ProjectService(IProjectRepository projectRepository, ISubProjectRepository subprojectRepository) {
        this.projectRepository = projectRepository;
        this.subprojectRepository = subprojectRepository;

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

    public boolean updateProject(Project newProject){
        return projectRepository.update(newProject);
    }

    public boolean deleteProject(int id){
        return projectRepository.delete(id);
    }

    public boolean doesProjectExist(int id){
        return projectRepository.doesProjectExist(id);
    }

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        return subprojectRepository.getSubProjectsByProjectID(projectId);
    }



}
