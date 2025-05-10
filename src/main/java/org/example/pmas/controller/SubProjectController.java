package org.example.pmas.controller;

import org.example.pmas.dto.SubProjectDTO;
import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/subprojects")
public class SubProjectController extends BaseController {

    private final SubProjectService subprojectService;

    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        super(subProjectService,projectService);
        this.subprojectService = subProjectService;
    }

    @GetMapping("/all")
    public String readAll(@PathVariable int projectId, Model model) {
        model.addAttribute(model.getAttribute("project"));
        List<SubProjectDTO> subprojects = subprojectService.getSubProjectDTOByProjectId(projectId);
        model.addAttribute("subprojects",subprojects);
        return "subprojects-all";
    }

    @GetMapping("/{subprojectId}/subproject")
    public String getSubProject(@PathVariable int subprojectId,
                                Model model) {
        validateId(subprojectId);
        SubProject subproject = subprojectService.readSelected(subprojectId);
        model.addAttribute("subproject", subproject);
        return "subproject-selected";
    }

    @PostMapping("/{subprojectId}/delete")
    public String deleteSubProject(@PathVariable int subprojectId,
                                   @PathVariable int projectId) {
        validateId(subprojectId);
        //int projectID = subprojectService.getProjectIDBySubProjectID(subprojectID);
        subprojectService.delete(subprojectId);
        return "redirect:/projects/" + projectId +"/subprojects/all";
    }

    @GetMapping("/new")
    public String createSubProject(@PathVariable int projectId,
                                   Model model) {
        validateId(projectId);
        SubProject subproject = new SubProject();
        subproject.setProjectID(projectId);

        model.addAttribute("subproject", subproject);
        model.addAttribute("projectID",projectId);
        return "subproject-new";
    }

    @PostMapping("/save")
    public String saveSubProject(@PathVariable int projectId,
                                 @ModelAttribute("subproject") SubProject subproject) {
        subproject.setProjectID(projectId);
        subprojectService.create(subproject);
        return "redirect:/projects/" + projectId + "/subprojects";
    }

    @GetMapping("/{subprojectId}/edit")
    public String editSubProject(@PathVariable int projectId, @PathVariable int subprojectId, Model model) {
        validateId(subprojectId);
        SubProject subproject = subprojectService.readSelected(subprojectId);
        model.addAttribute("subproject", subproject);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute SubProject subproject,
                                   @PathVariable(value="projectId") int projectId) {
        subprojectService.updateSubProject(subproject);
        return "redirect:/projects/" + projectId + "/subprojects/all";
    }

    private void validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Ugyldigt ID.");
    }
}
