package org.example.pmas.controller;

import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/subprojects")
public class SubProjectController {
    private final SubProjectService subProjectService;
    private final SessionHandler sessionHandler;

    public SubProjectController(SubProjectService subProjectService,
                                SessionHandler sessionHandler) {
        this.subProjectService = subProjectService;
        this.sessionHandler = sessionHandler;
    }

    @GetMapping("/all")
    public String readAll(@PathVariable(value = "projectId") int projectId,
                          Model model) {
        validateId(projectId);

        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {

            List<SubProjectDTO> subprojects = subProjectService.getSubProjectDTOByProjectId(projectId);
            model.addAttribute("subprojects", subprojects);
        }
        model.addAttribute("project", subProjectService.getProjectById(projectId));
        model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        model.addAttribute("allowAccess", loggedIn);
        return "subprojects-all";
    }

    @GetMapping("/{subprojectId}/subproject")
    public String getSubProject(@PathVariable(value = "projectId") int projectId,
                                @PathVariable(value = "subprojectId") int subprojectId,
                                Model model) {
        validateId(subprojectId);

        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            SubProject subproject = subProjectService.readSelected(subprojectId);
            model.addAttribute("subproject", subproject);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "subproject-selected";
    }

    @PostMapping("/{subprojectId}/delete")
    public String deleteSubProject(@PathVariable(value = "projectId") int projectId,
                                   @PathVariable(value = "subprojectId") int subprojectId) {
        validateId(subprojectId);

        if (sessionHandler.isNotAdmin()) {
            subProjectService.delete(subprojectId);
        }

        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    @GetMapping("/new")
    public String createSubProject(@PathVariable(value = "projectId") int projectId,
                                   Model model) {
        validateId(projectId);

        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            SubProject subproject = new SubProject();
            subproject.setProjectID(projectId);
            Project project = subProjectService.getProjectById(projectId);

            model.addAttribute("subproject", subproject);
            model.addAttribute("project", project);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "subproject-new";
    }

    @PostMapping("/create")
    public String saveSubProject(@PathVariable(value = "projectId") int projectId,
                                 @ModelAttribute SubProject subproject) {
        if (subproject == null) throw new IllegalArgumentException("Controller error: Something wrong with subproject.");

        if (sessionHandler.isNotAdmin()) {

            subproject.setProjectID(projectId);
            subProjectService.create(subproject);
        }

        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    @GetMapping("/{subprojectId}/edit")
    public String editSubProject(@PathVariable(value = "projectId") int projectId,
                                 @PathVariable(value = "subprojectId") int subprojectId,
                                 Model model) {
        validateId(subprojectId);

        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            SubProject subproject = subProjectService.readSelected(subprojectId);
            model.addAttribute("subproject", subproject);
        }
        model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        model.addAttribute("allowAccess", loggedIn);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute SubProject subproject,
                                   @PathVariable(value = "projectId") int projectId) {
        if (subproject == null) throw new IllegalArgumentException("Controller error: Something wrong with subproject.");

        if (sessionHandler.isNotAdmin()) {

            subProjectService.updateSubProject(subproject);
        }
        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    private void validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Controller error: Something wrong with id: " + id);
    }
}
