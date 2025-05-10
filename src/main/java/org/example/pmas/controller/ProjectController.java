package org.example.pmas.controller;

import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.User;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController extends BaseController {
    private final SessionHandler sessionHandler;


    public ProjectController(ProjectService projectService,
                             SubProjectService subProjectService,
                             SessionHandler sessionHandler) {
        super(subProjectService, projectService);
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
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        if (sessionHandler.isUserProjectManager()) {
            getProjectService().createProject(project);
        }

        return "redirect:/projects/all";
    }

    //TODO: ADD stuff so only the cto can see this page
    @GetMapping("/all")
    public String seeAll(Model model) {

        boolean allowAccess = sessionHandler.isUserProjectManager();
        List<Project> projects = getProjectService().readAll();
        model.addAttribute("projects", projects);
        model.addAttribute("allowAccess", allowAccess);

        return "project-all";
    }


    @GetMapping("/{projectId}/project")
    public String updateForm(@PathVariable int projectId, Model model) {
        Project project = getProjectService().readSelected(projectId);
        model.addAttribute("project", project);

        boolean allowAccess = sessionHandler.isUserProjectManager();
        model.addAttribute("allowAccess", allowAccess);
        return "project-update";
    }

    @PostMapping("{projectId}/delete")
    public String deleteProject(@PathVariable int projectId) {
        if (sessionHandler.isUserProjectManager()) {
            getProjectService().deleteProject(projectId);
        }
        return "redirect:/projects/all";
    }

    @PostMapping("update")
    public String updateProject(@ModelAttribute Project project) {
        if (sessionHandler.isUserProjectManager()) {
            getProjectService().updateProject(project);
        }

        return "redirect:/projects/all";
    }

    @GetMapping("/{projectId}/subprojects")
    public String viewSubProjects(@PathVariable int projectId, Model model) {
        List<SubProject> subprojects = getProjectService().getSubProjectsByProjectID(projectId);

        model.addAttribute("subprojects", subprojects);
        model.addAttribute("projectId", projectId);
        return "subprojects-all";
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
            projects = getProjectService().readProjectsOfUser(user.getUserID());
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
}
