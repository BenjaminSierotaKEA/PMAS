package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

public class BaseController {
    private final SubProjectService subProjectService;
    private final ProjectService projectService;

    // Sets services
    protected BaseController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
    }

    /*
        When a method from the child classes injects Model as parameter,
        this will be run.

        @PathVariable(value = "subprojectId"
       looks after the value, from the URL
    * */
    @ModelAttribute("subproject")
    public SubProject getSubProject(@PathVariable(value = "subprojectId", required = false) Integer subprojectId) {
        if (subprojectId != null) {
            return subProjectService.readSelected(subprojectId);
        }
        return null;
    }

    /*
       When a method from the child classes injects Model as parameter, in a method,
       this will run.

       @PathVariable(value = "projectId"
       looks after the value, from the URL
   * */
    @ModelAttribute("project")
    public Project getProject(@PathVariable(value = "projectId", required = false) Integer projectId) {
        if (projectId != null) {
            return projectService.readSelected(projectId);
        }
        return null;
    }
}
