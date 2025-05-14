package org.example.pmas.service;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.Project;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.service.comparators.ProjectDeadlineComparator;
import org.example.pmas.util.CompletionStatCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
         List<Project> projects = projectRepository.readAll();

         return sortList(projects);
    }

    public List<Project> readProjectsOfUser(int userID){
        List<Project> projects = projectRepository.readProjectsOfUser(userID);

        return sortList(projects);
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

    public List<ProjectDTO> getProjectDTOByUserID(int userID){
        List<ProjectDTO> projects = projectRepository.getProjectDTOByUserID(userID);

        CompletionStatCalculator calc = new CompletionStatCalculator();
        calc.calculateSubProjectCompletionPercentage(projects);

        for(ProjectDTO p : projects) {
            p.setCompletionPercentage(CompletionStatCalculator.calculatePercentage(p.getCompletedSubProjects(),p.getTotalSubProjects()));
            p.setCompleted(CompletionStatCalculator.isJobCompleted(p.getTimeTaken(), p.getTimeBudget()));
        }

        return projects;
    }

    // Sorts the list by deadline and then priority.
    // If the list is null, return an empty list. No errors
    private List<Project> sortList(List<Project> projects){
        // If the list is null, return an empty list. No errors
        if(projects == null) return Collections.emptyList();

        // Sort the list by deadline and then priority.
        List<Project> modifiableList = new ArrayList<>(projects);
        modifiableList.sort(new ProjectDeadlineComparator().reversed());

        return modifiableList;
    }
}
