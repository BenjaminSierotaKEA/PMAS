package org.example.pmas.service;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.exception.NotFoundException;
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

    public List<Project> readProjectsOfUser(int userID){
        return projectRepository.readProjectsOfUser(userID);
    }

    public Project readSelected(int id){
        Project project = projectRepository.readSelected(id);
        if (project == null) {
            throw new NotFoundException("Project with id " + id + " does not exist");
        }

        return project;
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

    public List<ProjectDTO> getProjectDTOByUserID(int userID){
        List<ProjectDTO> projects = projectRepository.getProjectDTOByUserID(userID);
        for(ProjectDTO p : projects) {
            calculateCompletionPercentage(p);
            checkIfCompleted(p);
        }
        return projects;
    }

    public void calculateCompletionPercentage(ProjectDTO dto) {
        if (dto.getTotalSubProjects() == 0) {
            dto.setCompletionPercentage(0);
        } else {
            double completionPercentage = 1.0 * dto.getCompletedSubProjects() * 100 / dto.getTotalSubProjects();
            dto.setCompletionPercentage(completionPercentage);
        }
    }

    public void checkIfCompleted(ProjectDTO dto) {
        if(dto.getTimeTaken() == dto.getTimeBudget()) {
            dto.setCompleted(true);
        }
    }
}
