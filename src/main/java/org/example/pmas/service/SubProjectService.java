package org.example.pmas.service;

import org.example.pmas.repository.Interfaces.ISubProjectRepository;

public class SubProjectService {

    private final ISubProjectRepository subProjectRepository;

    public SubProjectService(ISubProjectRepository subProjectRepository){
        this.subProjectRepository=subProjectRepository;
    }


}
