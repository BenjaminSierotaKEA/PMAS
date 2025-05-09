package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

// Because this is based and project, subproject and task inharen
public class BaseController {
    private final SubProjectService subProjectService;
    private final ProjectService projectService;

    // Sets services
    protected BaseController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
    }

    /*
    *  Gets the variabel from the RequestMapping above the class.
    * @RequestMapping("subprojects/{subprojectId}/tasks")
      public class TaskController extends BaseController {
    * */

    // When you inject Model in a parameter of a method, this will be loaded.
    @ModelAttribute("subproject")
    public SubProject getSubProject(@PathVariable(value = "subprojectId", required = false) Integer subprojectId) {
        if (subprojectId != null) {
            return subProjectService.readSelected(subprojectId);
        }
        return null;
    }

     /*
    *  Gets the variabel from the RequestMapping above the class.
    * @RequestMapping("projects/{projectId}/subprojects/{subprojectId}/tasks")
      public class TaskController extends BaseController {
    * */

    // When you inject Model in a parameter of a method, this will be loaded.
    @ModelAttribute("project")
    public Project getProject(@PathVariable(value = "projectId", required = false) Integer projectId) {
        if (projectId != null) {
            return projectService.readSelected(projectId);
        }
        return null;
    }
}
