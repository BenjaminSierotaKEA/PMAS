package org.example.pmas.service;

import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubProjectService {

    private final ISubProjectRepository subprojectRepository;

    public SubProjectService(ISubProjectRepository subProjectRepository){
        this.subprojectRepository = subProjectRepository;
    }

    public List<SubProject> readAll() {
        return subprojectRepository.readAll();
    }

    public SubProject readSelected(int id) {
        return subprojectRepository.readSelected(id);
    }

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        return subprojectRepository.getSubProjectsByProjectID(projectId);
    }

    public boolean delete(int id) {
        return subprojectRepository.delete(id);
    }

    public int getProjectIDBySubProjectID(int subprojectID){
        return subprojectRepository.getProjectIDBySubProjectID(subprojectID);
    }


}
