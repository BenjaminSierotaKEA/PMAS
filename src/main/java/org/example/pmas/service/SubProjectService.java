package org.example.pmas.service;

import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
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

    public boolean delete(int id) {
        if(!subprojectRepository.doesSubProjectExist(id)) {
            throw new NotFoundException(id);
        }
        return subprojectRepository.delete(id);
    }

    public boolean updateSubProject(SubProject subproject) {
        if(!subprojectRepository.doesSubProjectExist(subproject.getId())) {
            throw new NotFoundException(subproject.getId());
        }
        return subprojectRepository.update(subproject);
    }

    public List<SubProjectDTO> getSubProjectDTOByProjectId(int id) {
        List<SubProjectDTO> subprojects = subprojectRepository.getSubProjectDTOByProjectID(id);
        return subprojects;
    }
}
