package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("subprojects")
public class SubProjectController {

    private final SubProjectService subprojectService;

    public SubProjectController(SubProjectService subProjectService, TaskService taskService){
        this.subprojectService=subProjectService;
    }

    @GetMapping
    public String readAll(Model model) {
        model.addAttribute("subprojects", subprojectService.readAll());
        return "all-subprojects";
    }

    @GetMapping("/subproject/{id}")
    public String getSubProject(@PathVariable int id, Model model) {
        SubProject subproject = subprojectService.readSelected(id);
        model.addAttribute("subproject", subproject);
        return "subproject";
    }

    @GetMapping("/project/{projectId}/subprojects")
    public String getSubProjectsByProjectID(@PathVariable int projectId, Model model) {
        List<SubProject> subproject = subprojectService.getSubProjectsByProjectID(projectId);

        model.addAttribute("subproject", subproject);
        return "project-subprojects";
    }

    @PostMapping("/subprojects/{subprojectID}/delete")
    public String deleteSubProject(@PathVariable int subprojectID) {
        int projectID = subprojectService.getProjectIDBySubProjectID(subprojectID);
        subprojectService.delete(subprojectID);
        return "redirect:/project/" + projectID + "/subprojects";
    }




}
