package org.example.pmas.service;

import org.example.pmas.exception.CreateObjectException;
import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.exception.UpdateObjectException;
import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.util.CompletionStatCalculator;
import org.example.pmas.util.SortList;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SubProjectService {
    private final ISubProjectRepository subprojectRepository;
    private final IProjectRepository projectRepository;

    public SubProjectService(ISubProjectRepository subProjectRepository, IProjectRepository projectRepository) {
        this.subprojectRepository = subProjectRepository;
        this.projectRepository = projectRepository;
    }

    public SubProject readSelected(int id) {
        SubProject sub = subprojectRepository.readSelected(id);
        if (sub == null) {
            throw new NotFoundException(id);
        }
        return sub;
    }

    public void create(SubProject subproject) {
        if (!projectRepository.doesProjectExist(subproject.getProjectID())) {
            throw new NotFoundException(subproject.getProjectID());
        }

        if (subprojectRepository.create(subproject) == null)
            throw new CreateObjectException(subproject.getId());
    }

    public void delete(int id) {
        if (!subprojectRepository.doesSubProjectExist(id)) {
            throw new NotFoundException(id);
        }
        if (!subprojectRepository.delete(id))
            throw new DeleteObjectException(id);
    }

    public void updateSubProject(SubProject subproject) {
        if (!subprojectRepository.doesSubProjectExist(subproject.getId())) {
            throw new NotFoundException(subproject.getId());
        }

        if (!subprojectRepository.update(subproject))
            throw new UpdateObjectException(subproject.getId());
    }

    public List<SubProjectDTO> getSubProjectDTOByProjectId(int id) {
        List<SubProjectDTO> subprojects = subprojectRepository.getSubProjectDTOByProjectID(id);
        // if no list is returned, return empty list.
        if (subprojects == null) return Collections.emptyList();

        for (SubProjectDTO s : subprojects) {
            s.setCompletionPercentage(
                    CompletionStatCalculator.calculatePercentage(s.getCompletedTasks(), s.getTotalTasks())
            );
            s.setCompleted(
                    CompletionStatCalculator.isJobCompleted(s.getCompletedTasks(), s.getTotalTasks())
            );

            // Only on the last iteration update the completed in database.

            subprojectRepository.updateSubProjectCompleted(
                    s.getId(),
                    s.isCompleted());
        }
        return SortList.subProjectDTOName(subprojects);
    }


    public Project getProjectById(int projectId) {
        if (!projectRepository.doesProjectExist(projectId))
            throw new NotFoundException(projectId);

        return projectRepository.readSelected(projectId);
    }
}
