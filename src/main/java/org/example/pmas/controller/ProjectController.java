package org.example.pmas.controller;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.model.Project;
import org.example.pmas.model.User;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.UserService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;
    private final SessionHandler sessionHandler;

    public ProjectController(ProjectService projectService,
                             SessionHandler sessionHandler) {
        this.projectService = projectService;
        this.sessionHandler = sessionHandler;
        this.userService = userService;
    }

    //TODO: add session stuff
    @GetMapping("/new")
    public String getCreateProjectForm(Model model) {
        Project project = new Project();
        boolean allowAccess = sessionHandler.isUserProjectManager();

        //getting a list of users so we can add them to the project:
        if(allowAccess){
          List<User> users =  userService.getAll();
          List<Boolean> selections = new ArrayList<>();
          for(int i = 0; i < users.size(); i++){
              selections.add(false);
          }
          model.addAttribute("users", users);
          model.addAttribute("userSelections", selections);
        }

        model.addAttribute("allowAccess", allowAccess);
        model.addAttribute("project", project);

        return "project-create-form";
    }

    //TODO: add session stuff, redirect to somewhere better
    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project) {
        if (sessionHandler.isUserProjectManager()) {
            projectService.createProject(project);
        }

        return "redirect:/projects/all";
    }

    //TODO: ADD stuff so only the cto can see this page
    @GetMapping("/all")
    public String seeAll(Model model) {
        User user = sessionHandler.getCurrentUser();
        boolean allowAccess = sessionHandler.isUserProjectManager();
        if(allowAccess) {
            List<ProjectDTO> projects = projectService.getProjectDTOByUserID(user.getUserID());
            model.addAttribute("projects", projects);
        }
        model.addAttribute("allowAccess", allowAccess);
        return "project-all";
    }

    @GetMapping("/my-projects")
    public String myProjects(Model model) {
        User user = sessionHandler.getCurrentUser();
        boolean loggedIn = (user != null);

        List<Project> projects = null;
        if (loggedIn) {
            projects = projectService.readProjectsOfUser(user.getUserID());
            model.addAttribute("username", user.getName());
        } else {
            model.addAttribute("username", "logged out");
        }

        model.addAttribute("projects", projects);
        model.addAttribute("loggedIn", loggedIn);

        return "project-selected";

    }


    @GetMapping("/{projectId}/edit")
    public String updateForm(@PathVariable int projectId, Model model) {
        if(projectId <= 0) throw new IllegalArgumentException("Ugyldig ID.");

        if(!projectService.doesProjectExist(projectId)){
            return "errorpage";
        }else{

            Project project = projectService.readSelected(projectId);
            model.addAttribute("project", project);
            boolean allowAccess = sessionHandler.isUserProjectManager();
            //getting a list of users if we are allowed access:
            if(allowAccess){
                List<User> users =  userService.getAll();
                model.addAttribute("users", users);
            }

            model.addAttribute("allowAccess",allowAccess);
            return "project-update-form";
        }
    }

    @PostMapping("update")
    public String updateProject(@ModelAttribute Project project) {
        if(project == null) throw new IllegalArgumentException("Ugyldig proj    ekt.");

        if (sessionHandler.isUserProjectManager()) {
            projectService.updateProject(project);
        }

        return "redirect:/projects/all";
    }

    @PostMapping("{projectId}/delete")
    public String deleteProject(@PathVariable int projectId) {
        if(projectId <= 0) throw new IllegalArgumentException("Ugyldig ID.");

        if (sessionHandler.isUserProjectManager()) {
            projectService.deleteProject(projectId);
        }
        return "redirect:/projects/all";
    }

//    @GetMapping("/{projectId}/subprojects")
//    public String viewSubProjects(@PathVariable int projectId, Model model) {
//        if(projectId >= 0) throw new IllegalArgumentException("Ugyldig ID.");
//
//        List<SubProject> subprojects = projectService.getSubProjectsByProjectID(projectId);
//
//        model.addAttribute("subprojects", subprojects);
//        model.addAttribute("projectId", projectId);
//        return "subprojects-all";
//    }
}
