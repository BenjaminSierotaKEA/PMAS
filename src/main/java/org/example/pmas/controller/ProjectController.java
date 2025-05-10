package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.User;
import org.example.pmas.service.ProjectService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final SessionHandler sessionHandler;


    public ProjectController(ProjectService projectService, SessionHandler sessionHandler) {
        this.projectService = projectService;
        this.sessionHandler = sessionHandler;
    }

    //TODO: add session stuff
    @GetMapping("/new")
    public String getCreateProjectForm(Model model) {
        Project project = new Project();
        boolean allowAccess = sessionHandler.isUserProjectManager();
        model.addAttribute("allowAccess", allowAccess);
        model.addAttribute("project", project);

        return "project-new";
    }

    //TODO: add session stuff, redirect to somewhere better
    @PostMapping("/create-project")
    public String createProject(@ModelAttribute("project") Project project) {
        if (sessionHandler.isUserProjectManager()) {
            projectService.createProject(project);
        }


        return "redirect:projects/all";
    }

    //TODO: ADD stuff so only the cto can see this page
    @GetMapping("/all")
    public String seeAll(Model model) {

        boolean allowAccess = sessionHandler.isUserProjectManager();
        List<Project> projects = projectService.readAll();
        model.addAttribute("projects", projects);
        model.addAttribute("allowAccess", allowAccess);

        return "project-all";
    }

    @GetMapping("/my-projects")
    public String myProjects(Model model) {


        User user = sessionHandler.getCurrentUser();
        boolean loggedIn = false;
        if (user.getRole() != null) {
            loggedIn = true;
        }
        List<Project> projects = null;
        if (!(user == null)) {
            projects = projectService.readProjectsOfUser(user.getUserID());
        }
        if (loggedIn) {
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username", "logged out");
        }

        model.addAttribute("projects", projects);
        model.addAttribute("loggedIn", loggedIn);

        return "project-selected";

    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable int id, Model model) {


        if (!projectService.doesProjectExist(id)) {
            return "errorpage";
        }

        Project project = projectService.readSelected(id);
        model.addAttribute("project", project);
        boolean allowAccess = sessionHandler.isUserProjectManager();
        model.addAttribute("allowAccess", allowAccess);
        return "project-update";
    }

    @PostMapping("update-project")
    public String updateProject(@ModelAttribute Project project, Model model) {


        if (sessionHandler.isUserProjectManager()) {
            projectService.updateProject(project);
        }

        return "redirect:see-all";
    }

    @PostMapping("delete-project")
    public String deleteProject(@ModelAttribute Project project, Model model) {
        if (sessionHandler.isUserProjectManager()) {
            projectService.deleteProject(project.getId());
        }
        return "redirect:all";
    }

    @GetMapping("/{projectId}/subprojects")
    public String viewSubProjects(@PathVariable int projectId, Model model) {
        List<SubProject> subprojects = projectService.getSubProjectsByProjectID(projectId);

        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectId", projectId);
        return "subprojects-all";
    }
}
