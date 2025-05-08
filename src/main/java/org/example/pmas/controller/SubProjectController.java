package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.service.SubProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
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
        validateId(id);
        SubProject subproject = subprojectService.readSelected(id);
        model.addAttribute("subproject", subproject);
        return "subproject-selected";
    }

    @PostMapping("/{subprojectId}/delete")
    public String deleteSubProject(@PathVariable int subprojectId) {
        validateId(subprojectId);
        //int projectID = subprojectService.getProjectIDBySubProjectID(subprojectID);
        subprojectService.delete(subprojectId);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/subprojects/create")
    public String createSubProject(@PathVariable int projectId, Model model) {
        validateId(projectId);
        SubProject subproject = new SubProject();
        subproject.setProjectID(projectId);

        model.addAttribute("subproject", subproject);
        model.addAttribute("projectID",projectId);
        return "subproject-new";
    }

    @PostMapping("/save")
    public String saveSubProject(@ModelAttribute("subproject") SubProject subproject) {
        subprojectService.create(subproject);
        return "redirect:/projects/" + subproject.getProjectID() + "/subprojects";
    }

    @GetMapping("/{id}/edit")
    public String editSubProject(@PathVariable int id, Model model) {
        validateId(id);
        SubProject subproject = subprojectService.readSelected(id);
        model.addAttribute("subproject", subproject);
        return "subproject-edit-form";
    }

    @PostMapping("/update")
    public String updateSubProject(@ModelAttribute("subproject") SubProject subproject) {
        subprojectService.updateSubProject(subproject);
        return "redirect:/projects/" + subproject.getProjectID() + "/subprojects";
    }

    @GetMapping("/{subprojectId}/tasks")
    public String viewTasks(@PathVariable int subprojectId, Model model) {
        validateId(subprojectId);
        List<Task> tasks = subprojectService.getTasksBySubProjectID(subprojectId);

        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectId", subprojectId);
        return "task-all";
    }

    private void validateId(int id) {
        if (id <= 0) throw new IllegalArgumentException("Ugyldigt ID.");
    }
}
