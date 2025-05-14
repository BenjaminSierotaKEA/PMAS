package org.example.pmas.controller;

import org.example.pmas.dto.SubProjectDTO;
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
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            validateId(projectId);
            model.addAttribute("project", subProjectService.getProjectById(projectId));
            List<SubProjectDTO> subprojects = subProjectService.getSubProjectDTOByProjectId(projectId);
            model.addAttribute("subprojects", subprojects);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "subprojects-all";
    }

    @GetMapping("/{subprojectId}/subproject")
    public String getSubProject(@PathVariable(value = "projectId") int projectId,
                                @PathVariable(value = "subprojectId") int subprojectId,
                                Model model) {
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            validateId(subprojectId);
            SubProject subproject = subProjectService.readSelected(subprojectId);
            model.addAttribute("subproject", subproject);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "subproject-selected";
    }

    @PostMapping("/{subprojectId}/delete")
    public String deleteSubProject(@PathVariable(value = "projectId") int projectId,
                                   @PathVariable(value = "subprojectId") int subprojectId) {
        if (sessionHandler.isNotAdmin()) {
            validateId(subprojectId);
            subProjectService.delete(subprojectId);
        }

        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    @GetMapping("/new")
    public String createSubProject(@PathVariable(value = "projectId") int projectId,
                                   Model model) {
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            validateId(projectId);
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
        if (sessionHandler.isNotAdmin()) {
            if (subproject == null) throw new IllegalArgumentException("Something wrong with subproject.");

            subproject.setProjectID(projectId);
            subProjectService.create(subproject);
        }

        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    @GetMapping("/{subprojectId}/edit")
    public String editSubProject(@PathVariable(value = "projectId") int projectId,
                                 @PathVariable(value = "subprojectId") int subprojectId,
                                 Model model) {
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            validateId(subprojectId);
            SubProject subproject = subProjectService.readSelected(subprojectId);
            model.addAttribute("subproject", subproject);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute SubProject subproject,
                                   @PathVariable(value = "projectId") int projectId) {
        if (sessionHandler.isNotAdmin()) {
            if (subproject == null) throw new IllegalArgumentException("Something wrong with subproject.");

            subProjectService.updateSubProject(subproject);
        }
        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    private void validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Something wrong with id in URL.");
    }
}
