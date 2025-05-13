package org.example.pmas.repository.Interfaces;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IProjectRepository extends CrudInterface<Project> {
    boolean doesProjectExist(int id);

    List<Project> readProjectsOfUser(int userID);
    List<ProjectDTO> getProjectDTOByUserID(int userID);
}
