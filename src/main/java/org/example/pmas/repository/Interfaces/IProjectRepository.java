package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Project;
import org.springframework.stereotype.Repository;


@Repository
public interface IProjectRepository extends CrudInterface<Project> {
    boolean doesProjectExist(int id);
}
