package org.example.pmas.service;

import org.example.pmas.exception.ProjectNotFoundException;
import org.example.pmas.exception.SubProjectNotFoundException;
import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
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

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        return subprojectRepository.getSubProjectsByProjectID(projectId);
    }

    public SubProject readSelected(int id) {
        SubProject sub = subprojectRepository.readSelected(id);
        if(sub == null) {
            throw new SubProjectNotFoundException(id);
        }
        return sub;
    }

    public int getProjectIDBySubProjectID(int subprojectID){
        return subprojectRepository.getProjectIDBySubProjectID(subprojectID);
    }

    public SubProject create(SubProject subproject) {
        if(!projectRepository.doesProjectExist(subproject.getProjectID())) {
            throw new ProjectNotFoundException(subproject.getProjectID());
        }
        return subprojectRepository.create(subproject);
    }

    public boolean delete(int id) {
        return subprojectRepository.delete(id);
    }
}
