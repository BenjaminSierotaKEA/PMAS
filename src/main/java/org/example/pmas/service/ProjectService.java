package org.example.pmas.service;

import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.UpdateObjectException;
import org.example.pmas.model.dto.ProjectDTO;

import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.Project;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IProjectRepository;

import org.example.pmas.repository.Interfaces.IUserRepository;

import org.example.pmas.util.CompletionStatCalculator;
import org.example.pmas.util.SortList;
import org.springframework.stereotype.Service;

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

    public Project createProject(Project project) {
        return projectRepository.create(project);
    }

    public void addUsersToProject(int projectID, Set<Integer> userIDs) {
        if (userIDs != null) {
            projectRepository.addUsersToProject(projectID, userIDs);
        }
    }

    public void removeUsersFromProject(int projectID, Set<Integer> userIds) {
        if (userIds != null) {
            projectRepository.removeUsersFromProject(projectID, userIds);
        }
    }

    public List<Project> readAll() {
        List<Project> projects = projectRepository.readAll();

        return SortList.projectsDeadline(projects);
    }

    public Project readSelected(int id) {
        Project project = projectRepository.readSelected(id);
        if (project == null) {
            throw new NotFoundException(id);
        }

        return project;
    }

    public List<User> getAllUsersOnProject(int projectID) {
        List<User> users = userRepository.getAllOnProject(projectID);

        return SortList.userName(users);
    }

    public List<User> getAllUsersNotOnProject(int projectID) {
        List<User> users = userRepository.getAllNotOnProject(projectID);

        return SortList.userName(users);
    }

    public void updateProject(Project newProject) {
        if (!projectRepository.doesProjectExist(newProject.getId()))
            throw new NotFoundException(newProject.getId());

        if (!projectRepository.update(newProject))
            throw new UpdateObjectException(newProject.getId());
    }

    public void deleteProject(int id) {
        if (!projectRepository.doesProjectExist(id))
            throw new NotFoundException(id);

        if (!projectRepository.delete(id))
            throw new DeleteObjectException(id);
    }

    public List<ProjectDTO> getProjectDTOByUserID(int userID) {
        // Returns null if no list
        List<ProjectDTO> projects = projectRepository.getProjectDTOByUserID(userID);
        // if no list is returned, return an empty list.
        if (projects == null) return Collections.emptyList();

        for (ProjectDTO p : projects) {
            p.setCompletionPercentage(
                    CompletionStatCalculator.calculatePercentage(p.getCompletedSubProjects(), p.getTotalSubProjects())
            );
            p.setCompleted(
                    CompletionStatCalculator.isJobCompleted(p.getCompletedSubProjects(), p.getTotalSubProjects())
            );

            projectRepository.updateProjectCompleted(
                    p.getId(),
                    p.isCompleted()
            );
        }

        return SortList.projectsDTODeadline(projects);
    }

    public List<User> getAllUsers() {
        return userRepository.readAll();
    }

    public boolean checkProjectName(String name){
        return projectRepository.checkProjectName(name);
    }
}
