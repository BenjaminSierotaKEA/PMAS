package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Project;


public interface IProjectRepository extends CrudInterface<Project> {
    boolean doesProjectExist(int id);
}
