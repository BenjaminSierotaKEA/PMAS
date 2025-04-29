package org.example.pmas.service;

import org.example.pmas.repository.SubProjectRepository;

public class SubProjectService {

    private final SubProjectRepository subProjectRepository;

    public SubProjectService(SubProjectRepository subProjectRepository){
        this.subProjectRepository=subProjectRepository;
    }


}
