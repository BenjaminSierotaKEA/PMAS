package org.example.pmas.controller;

import jakarta.servlet.http.HttpSession;
import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
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


    public ProjectController(ProjectService projectService, UserService userService, SessionHandler sessionHandler){
        this.projectService = projectService;
        this.sessionHandler = sessionHandler;
        this.userService = userService;
    }

    //TODO: add session stuff
    @GetMapping("/create-project-form")
    public String getCreateProjectForm(Model model){
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

        return "create-project-form";
    }

    //TODO: add session stuff, redirect to somewhere better
    @PostMapping("/create-project")
    public String createProject(@ModelAttribute("project") Project project){
        if(sessionHandler.isUserProjectManager()){
            projectService.createProject(project);
        }


        return "redirect:see-all";
    }

    //TODO: ADD stuff so only the cto can see this page
    @GetMapping("/see-all")
    public String seeAll(Model model){

        boolean allowAccess = sessionHandler.isUserProjectManager();
        List<Project> projects = projectService.readAll();
        model.addAttribute("projects", projects);
        model.addAttribute("allowAccess", allowAccess);

        return "show-all-projects";
    }

    @GetMapping("/my-projects")
    public String myProjects(Model model, HttpSession session){


        User user = sessionHandler.getCurrentUser();
        boolean loggedIn = false;
        if(user != null){
            loggedIn = true;
        }
        List<Project> projects = null;
        if(!(user==null)){
          projects =  projectService.readProjectsOfUser(user.getUserID());
        }
        if(loggedIn){
            model.addAttribute("username", user.getName());
        }else{
            model.addAttribute("username", "logged out");
        }

        model.addAttribute("projects", projects);
        model.addAttribute("loggedIn", loggedIn);

        return "my-projects";

    }

    @GetMapping("/{id}/update-form")
    public String updateForm(@PathVariable int id, Model model){


        if(!projectService.doesProjectExist(id)){
            return "project-does-not-exist";
        }else{

            Project project = projectService.readSelected(id);
            model.addAttribute("project", project);
            boolean allowAccess = sessionHandler.isUserProjectManager();
            //getting a list of users if we are allowed access:
            if(allowAccess){
                List<User> users =  userService.getAll();
                model.addAttribute("users", users);
            }

            model.addAttribute("allowAccess",allowAccess);
            return "update-project-form";
        }
        //Project project = projectService.
    }

    @PostMapping("update-project")
    public String updateProject(@ModelAttribute Project project, Model model){


        if(sessionHandler.isUserProjectManager()){
            projectService.updateProject(project);
        }

        return "redirect:see-all";
    }

    @PostMapping("delete-project")
    public String deleteProject(@ModelAttribute Project project, Model model){
        if(sessionHandler.isUserProjectManager()){
            projectService.deleteProject(project.getId());
        }
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
