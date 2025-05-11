package org.example.pmas.controller;

import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.model.Project;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/subprojects")
public class SubProjectController {
    private final SubProjectService subProjectService;
    private final ProjectService projectService;

    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        this.subProjectService = subProjectService;
        this.projectService = projectService;
    }

    @GetMapping("/all")
    public String readAll(@PathVariable(value = "projectId") int projectId,
                          Model model) {
        model.addAttribute("project", projectService.readSelected(projectId));
        List<SubProjectDTO> subprojects = subProjectService.getSubProjectDTOByProjectId(projectId);
        model.addAttribute("subprojects",subprojects);
        return "subprojects-all";
    }

    @GetMapping("/{subprojectId}/subproject")
    public String getSubProject(@PathVariable(value = "projectId") int projectId,
                                @PathVariable(value = "subprojectId") int subprojectId,
                                Model model) {
        validateId(subprojectId);
        SubProject subproject = subProjectService.readSelected(subprojectId);
        model.addAttribute("subproject", subproject);
        return "subproject-selected";
    }

    @PostMapping("/{subprojectId}/delete")
    public String deleteSubProject(@PathVariable(value = "projectId") int projectId,
                                   @PathVariable(value = "subprojectId") int subprojectId) {
        validateId(subprojectId);
        //int projectID = subprojectService.getProjectIDBySubProjectID(subprojectID);
        subProjectService.delete(subprojectId);
        return "redirect:/projects/" + projectId +"/subprojects/all";
    }

    @GetMapping("/new")
    public String createSubProject(@PathVariable(value = "projectId") int projectId,
                                   Model model) {
        validateId(projectId);
        SubProject subproject = new SubProject();
        subproject.setProjectID(projectId);
        Project project = projectService.readSelected(projectId);

        model.addAttribute("subproject", subproject);
        model.addAttribute("project",project);
        return "subproject-new";
    }

    @PostMapping("/create")
    public String saveSubProject(@PathVariable(value = "projectId") int projectId,
                                 @ModelAttribute SubProject subproject) {
        subproject.setProjectID(projectId);
        subProjectService.create(subproject);
        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    @GetMapping("/{subprojectId}/edit")
    public String editSubProject(@PathVariable(value = "projectId") int projectId,
                                 @PathVariable(value = "subprojectId") int subprojectId,
                                 Model model) {
        validateId(subprojectId);
        SubProject subproject = subProjectService.readSelected(subprojectId);
        model.addAttribute("subproject", subproject);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute SubProject subproject,
                                   @PathVariable(value = "projectId") int projectId) {
        subProjectService.updateSubProject(subproject);
        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    private void validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Ugyldigt ID.");
    }
}
