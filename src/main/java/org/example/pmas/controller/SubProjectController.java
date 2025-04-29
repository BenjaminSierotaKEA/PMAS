package org.example.pmas.controller;

import org.example.pmas.service.SubProjectService;

public class SubProjectController {

    private final SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService){
        this.subProjectService=subProjectService;
    }

}
