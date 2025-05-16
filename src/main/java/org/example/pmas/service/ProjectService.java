package org.example.pmas.service;

import org.example.pmas.model.dto.ProjectDTO;

import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.Project;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IProjectRepository;

import org.example.pmas.repository.Interfaces.IUserRepository;

import org.example.pmas.service.comparators.ProjectDTODeadlineComparator;
import org.example.pmas.service.comparators.ProjectDeadlineComparator;
import org.example.pmas.util.CompletionStatCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final IProjectRepository projectRepository;
    private final IUserRepository userRepository;


    public ProjectService(IProjectRepository projectRepository, IUserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;

    }

    public Project createProject(Project project){
        return projectRepository.create(project);
    }

    public void addUsersToProject(int projectID, Set<Integer> userIDs){
        if(userIDs != null){
            projectRepository.addUsersToProject(projectID, userIDs);
        }

    }

    public void removeUsersFromProject(int projectID, Set<Integer> userIds){
        if(userIds != null){
            projectRepository.removeUsersFromProject(projectID, userIds);
        }

    }

    public List<Project> readAll(){
         List<Project> projects = projectRepository.readAll();

         return sortList(projects);
    }

    public Project readSelected(int id){
        Project project = projectRepository.readSelected(id);
        if (project == null) {
            throw new NotFoundException("Project with id " + id + " does not exist");
        }

        return project;
    }

    public List<User> getAllUsersOnProject(int projectID){
        return userRepository.getAllOnProject(projectID);
    }

    public List<User> getAllUsersNotOnProject(int projectID){
        return userRepository.getAllNotOnProject(projectID);
    }

    public boolean updateProject(Project newProject){
        if(!projectRepository.doesProjectExist(newProject.getId()))
            throw new NotFoundException("Project with id " + newProject.getId() + " does not exist");

        return projectRepository.update(newProject);
    }

    public boolean deleteProject(int id){
        if(!projectRepository.doesProjectExist(id))
            throw new NotFoundException("Project with id " + id + " does not exist");

        return projectRepository.delete(id);
    }

    public boolean doesProjectExist(int id){
        return projectRepository.doesProjectExist(id);
    }

    public List<ProjectDTO> getProjectDTOByUserID(int userID){
        // Returns null if no list
        List<ProjectDTO> projects = projectRepository.getProjectDTOByUserID(userID);
        if(projects == null) return Collections.emptyList();

        for(ProjectDTO p : projects) {
            p.setCompletionPercentage(CompletionStatCalculator.calculatePercentage(p.getCompletedSubProjects(),p.getTotalSubProjects()));
            p.setCompleted(CompletionStatCalculator.isJobCompleted(p.getCompletedSubProjects(), p.getTotalSubProjects()));
        }

        // Sort the list by deadline
        // We copy the list so its not immutable
        List<ProjectDTO> modifiableList = new ArrayList<>(projects);
        modifiableList.sort(new ProjectDTODeadlineComparator().reversed());
        return modifiableList;
    }

    public List<User> getAllUsers(){
        return userRepository.readAll();
    }

    // Sorts the list by deadline.
    // If the list is null, return an empty list. No errors
    private List<Project> sortList(List<Project> projects){
        // If the list is null, return an empty list. No errors
        if(projects == null) return Collections.emptyList();

        // Sort the list by deadline.
        // We copy the list so its not immutable
        List<Project> modifiableList = new ArrayList<>(projects);
        modifiableList.sort(new ProjectDeadlineComparator().reversed());

        return modifiableList;
    }


}
