package org.example.pmas.service;

import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.exception.UpdateObjectException;
import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.util.CompletionStatCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {
    private final ISubProjectRepository subprojectRepository;
    private final IProjectRepository projectRepository;

    public SubProjectService(ISubProjectRepository subProjectRepository, IProjectRepository projectRepository) {
        this.subprojectRepository = subProjectRepository;
        this.projectRepository = projectRepository;
    }

    public List<SubProject> readAll() {
        return subprojectRepository.readAll();
    }

    public SubProject readSelected(int id) {
        SubProject sub = subprojectRepository.readSelected(id);
        if(sub == null) {
            throw new NotFoundException(id);
        }
        return sub;
    }

    public SubProject create(SubProject subproject) {
        if(!projectRepository.doesProjectExist(subproject.getProjectID())) {
            throw new NotFoundException(subproject.getProjectID());
        }
        return subprojectRepository.create(subproject);
    }

    public void delete(int id) {
        if(!subprojectRepository.doesSubProjectExist(id)) {
            throw new NotFoundException(id);
        }
        if(!subprojectRepository.delete(id))
            throw new DeleteObjectException("Couldn't delete subproject with id: " + id);
    }

    public void updateSubProject(SubProject subproject) {
        if(!subprojectRepository.doesSubProjectExist(subproject.getId())) {
            throw new NotFoundException(subproject.getId());
        }

        if(!subprojectRepository.update(subproject))
            throw new UpdateObjectException("Couldn't update subproject with id: " + subproject.getId());
    }

    public List<SubProjectDTO> getSubProjectDTOByProjectId(int id) {
        List<SubProjectDTO> subprojects = subprojectRepository.getSubProjectDTOByProjectID(id);
        CompletionStatCalculator<SubProjectDTO> calc = new CompletionStatCalculator<>();
        calc.calculateTaskCompletionPercentage(subprojects);
        return subprojects;
    }

    public Project getProjectById(int projectId){
        if(!projectRepository.doesProjectExist(projectId)) throw new NotFoundException("Something wrong with project id");
        return projectRepository.readSelected(projectId);
    }
}
