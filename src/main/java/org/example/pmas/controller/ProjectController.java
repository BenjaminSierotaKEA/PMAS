package org.example.pmas.controller;

import org.example.pmas.model.dto.ProjectDTO;
import org.example.pmas.model.Project;
import org.example.pmas.model.User;
import org.example.pmas.service.ProjectService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final SessionHandler sessionHandler;

    public ProjectController(ProjectService projectService,
                             SessionHandler sessionHandler) {
        this.projectService = projectService;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping("/new")
    public String getCreateProjectForm(Model model) {
        Project project = new Project();
        boolean allowAccess = sessionHandler.isUserProjectManager();


        model.addAttribute("allowAccess", allowAccess);
        if (!model.containsAttribute("project"))
            model.addAttribute("project", project);
        model.addAttribute("users", projectService.getAllUsers());

        return "project-create-form";
    }

    // RedirectAttribute explained
    // stores the attributes in a flashmap (which is internally maintained in the users session and
    // removed once the next redirected request gets fulfilled)
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project,
                                @RequestParam(name = "userIds", required = false) Set<Integer> userIDs,
                                RedirectAttributes redirectAttributes) {
        if (project == null) throw new IllegalArgumentException("Controller error: Something wrong with project.");
        if (checkNameAndUsers(userIDs, project, redirectAttributes)) {
            return "redirect:/projects/new";
        }

        if (sessionHandler.isUserProjectManager()) {
            Project resultProject = projectService.createProject(project);
            projectService.addUsersToProject(resultProject.getId(), userIDs); //replace 1 with ID of newly created project
        }

        return "redirect:/projects/my-projects";
    }

    // Checks for name and users
    private boolean checkNameAndUsers(Set<Integer> userIDs, Project project, RedirectAttributes redirectAttributes) {
        boolean nameExists = projectService.checkProjectName(project.getName());
        if (nameExists) {
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("selectedUserIds", userIDs);
            redirectAttributes.addFlashAttribute("errorName", "Project name already exists. Please choose another name.");
        }
        if (userIDs == null || userIDs.isEmpty()) {
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("errorUser", "Please choose at least one user to assign the project to.");
        }

        return nameExists || userIDs == null || userIDs.isEmpty();
    }

    @GetMapping("/all")
    public String seeAll(Model model) {
        boolean allowAccess = sessionHandler.isNotAdmin();
        if (allowAccess) {
            List<Project> projects = projectService.readAll();
            model.addAttribute("projects", projects);
        }
        model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        model.addAttribute("allowAccess", allowAccess);
        return "project-all";
    }

    @GetMapping("/my-projects")
    public String myProjects(Model model) {
        User user = sessionHandler.getCurrentUser();
        boolean loggedIn = (user != null);

        if (loggedIn) {
            List<ProjectDTO> projects = projectService.getProjectDTOByUserID(user.getUserID());
            model.addAttribute("username", user.getName());
            model.addAttribute("projects", projects);
        } else {
            model.addAttribute("username", "logged out");
        }
        model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        model.addAttribute("loggedIn", loggedIn);
        return "project-selected";
    }


    @GetMapping("/{projectId}/edit")
    public String updateForm(@PathVariable int projectId, Model model) {
        if (projectId <= 0) throw new IllegalArgumentException("Controller error: Something wrong with id: " + projectId);

        Project project;

        boolean allowAccess = sessionHandler.isUserProjectManager();
        //getting a list of users if we are allowed access:
        List<User> usersOnProject = new ArrayList<>();
        List<User> usersNotOnProject = new ArrayList<>();
        if (allowAccess) {
            project = projectService.readSelected(projectId);
            if (!model.containsAttribute("project"))
                model.addAttribute("project", project);
            usersOnProject = projectService.getAllUsersOnProject(projectId);
            usersNotOnProject = projectService.getAllUsersNotOnProject(projectId);

        }

        model.addAttribute("allowAccess", allowAccess);
        model.addAttribute("usersOnProject", usersOnProject);
        model.addAttribute("usersNotOnProject", usersNotOnProject);
        return "project-update-form";
    }


    // RedirectAttribute explained
    // stores the attributes in a flashmap (which is internally maintained in the users session and
    // removed once the next redirected request gets fulfilled)
    @PostMapping("update")
    public String updateProject(@ModelAttribute Project project,
                                @RequestParam(name = "usersToAddID", required = false) Set<Integer> usersToAddID,
                                @RequestParam(name = "usersToRemoveID", required = false) Set<Integer> usersToRemoveID,
                                @RequestParam(name="originalName") String originalName,
                                RedirectAttributes redirectAttributes) {
        if (project == null) throw new IllegalArgumentException("Controller error: Something wrong with project");
        if (projectService.checkProjectName(project.getName()) && !project.getName().equals(originalName)) {
            redirectAttributes.addFlashAttribute("project", project);
            redirectAttributes.addFlashAttribute("errorName", "Project name already exists. Please choose another name.");
            return "redirect:/projects/" + project.getId() + "/edit";
        }

        if (sessionHandler.isUserProjectManager()) {

            projectService.updateProject(project);
            if (usersToAddID != null) {
                projectService.addUsersToProject(project.getId(), usersToAddID);
            }
            if (usersToRemoveID != null) {
                projectService.removeUsersFromProject(project.getId(), usersToRemoveID);
            }

        }

        return "redirect:/projects/my-projects";
    }

    @PostMapping("{projectId}/delete")
    public String deleteProject(@PathVariable int projectId) {
        if (projectId <= 0) throw new IllegalArgumentException("Controller error: Something wrong with id: " + projectId);

        if (sessionHandler.isUserProjectManager()) {
            projectService.deleteProject(projectId);
        }
        return "redirect:/projects/my-projects";
    }
}
