package org.example.pmas.repository.Interfaces;


import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.model.SubProject;

import java.util.List;

public interface ISubProjectRepository extends CrudInterface<SubProject> {
    boolean doesSubProjectExist(int Id);

    List<SubProjectDTO> getSubProjectDTOByProjectID(int id);

    void updateSubProjectCompleted(int subProjectID, boolean completed);
}
