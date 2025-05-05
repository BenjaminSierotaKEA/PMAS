package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService){
        this.projectService=projectService;
    }

    //TODO: add session stuff
    @GetMapping("/create-project-form")
    public String getCreateProjectForm(Model model){
        Project project = new Project();
        model.addAttribute("project", project);

        return "create-project-form";
    }

    //TODO: add session stuff, redirect to somewhere better
    @PostMapping("/create-project")
    public String createProject(@ModelAttribute("project") Project project){

        projectService.createProject(project);

        return "redirect:see-all";
    }

    //TODO: ADD stuff so only the cto can see this page
    @GetMapping("/see-all")
    public String seeAll(Model model){
        List<Project> projects = projectService.readAll();
        model.addAttribute("projects", projects);

        return "show-all-projects";
    }

    @GetMapping("/{id}/update-form")
    public String updateForm(@PathVariable int id, Model model){


        if(!projectService.doesProjectExist(id)){
            return "project-does-not-exist";
        }else{

            Project project = projectService.readSelected(id);
            model.addAttribute(project);
            return "update-project-form";
        }
        //Project project = projectService.
    }

    @PostMapping("update-project")
    public String updateProject(@ModelAttribute Project project, Model model){


        //until i figure out how to send both the old project and the new project,
        //we just send the new project twice. all we need from the old project is
        //the id, which should be the same as the new project no matter what.
        projectService.updateProject(project, project);


        return "redirect:see-all";
    }

    @PostMapping("delete-project")
    public String deleteProject(@ModelAttribute Project project, Model model){
        projectService.deleteProject(project.getId());
        return "redirect:see-all";
    }

    @GetMapping("/{projectId}/subprojects")
    public String viewSubProjects(@PathVariable int projectId, Model model) {
        List<SubProject> subprojects = projectService.getSubProjectsByProjectID(projectId);

        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectId", projectId);
        return "subprojects-all";
    }

}
