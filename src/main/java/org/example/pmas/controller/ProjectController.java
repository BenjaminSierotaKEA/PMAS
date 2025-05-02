package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "redirect: /projects/create-project-form";
    }

}
