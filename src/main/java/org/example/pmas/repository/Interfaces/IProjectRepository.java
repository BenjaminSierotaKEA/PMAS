package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.dto.ProjectDTO;
import org.example.pmas.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface IProjectRepository extends CrudInterface<Project> {
    boolean doesProjectExist(int id);

    List<Project> readProjectsOfUser(int userID);

    void addUsersToProject(int projectID, Set<Integer> userIDs);

    void removeUsersFromProject(int projectID, Set<Integer> userIDs);
    List<ProjectDTO> getProjectDTOByUserID(int userID);
    //List<ProjectDTO> getAllProjectDTO();
}
