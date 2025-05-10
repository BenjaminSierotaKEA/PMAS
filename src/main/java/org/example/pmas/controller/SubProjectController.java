package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("projects/{projectId}/subprojects")
public class SubProjectController extends BaseController {

    private final SubProjectService subprojectService;

    public SubProjectController(SubProjectService subProjectService, ProjectService projectService) {
        super(subProjectService,projectService);
        this.subprojectService = subProjectService;
    }

    @GetMapping("/all")
    public String readAll(Model model) {
        model.addAttribute("subprojects", subprojectService.readAll());
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

    @PostMapping("/create")
    public String saveSubProject(@ModelAttribute SubProject subproject,
                                 @PathVariable(value = "projectId", required = false) int projectId) {
        subprojectService.create(subproject);
        return "redirect:/projects/" + projectId + "/subprojects/all";
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
