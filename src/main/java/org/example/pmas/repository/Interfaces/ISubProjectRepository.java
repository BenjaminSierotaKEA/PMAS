package org.example.pmas.repository.Interfaces;


import org.example.pmas.model.SubProject;

import java.util.List;

public interface ISubProjectRepository extends CrudInterface<SubProject> {
    List<SubProject> getSubProjectsByProjectID(int projectId);
    int getProjectIDBySubProjectID(int subProjectId);
    boolean doesSubProjectExist(int projectId);
    int updateSubProject(SubProject subproject);
}
