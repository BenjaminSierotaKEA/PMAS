package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("subprojects")
public class SubProjectController {

    private final SubProjectService subprojectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subprojectService = subProjectService;
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("subprojects", subprojectService.readAll());
        return "subprojects-all";
    }

    @GetMapping("/{id}")
    public String getSubProject(@PathVariable int id, Model model) {
        SubProject subproject = subprojectService.readSelected(id);
        model.addAttribute("subproject", subproject);
        return "subproject-selected";
    }

    @GetMapping("/project/{projectId}/subprojects")
    public String getSubProjectsByProjectID(@PathVariable int projectId, Model model) {
        List<SubProject> subproject = subprojectService.getSubProjectsByProjectID(projectId);

        model.addAttribute("subproject", subproject);
        return "project-subprojects-selected";
    }

    @PostMapping("/{subprojectID}/delete")
    public String deleteSubProject(@PathVariable int subprojectID) {
        //int projectID = subprojectService.getProjectIDBySubProjectID(subprojectID);
        subprojectService.delete(subprojectID);
        return "redirect:/subprojects";
    }

    @GetMapping("/{projectID}/add")
    public String createSubProject(@PathVariable int projectID, Model model) {
        SubProject subproject = new SubProject();
        subproject.setProjectID(projectID);

        model.addAttribute("subproject", subproject);
        return "subproject-new";
    }

    @PostMapping("/save")
    public String saveSubProject(@ModelAttribute("subproject") SubProject subproject) {
        subprojectService.create(subproject);
        return "redirect:/subprojectsall";
    }

    @GetMapping("/{id}/edit")
    public String editSubProject(@PathVariable int id, Model model) {
        SubProject subproject = subprojectService.readSelected(id);
        model.addAttribute("subproject", subproject);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute("subproject") SubProject subproject) {
        subprojectService.updateSubProject(subproject);
        return "redirect:/subprojects/" + subproject.getId();
    }
}
