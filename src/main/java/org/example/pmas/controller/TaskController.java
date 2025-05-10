package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.enums.PriorityLevel;
import org.example.pmas.service.ProjectService;
import org.example.pmas.service.SubProjectService;
import org.example.pmas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/subprojects/{subprojectId}/tasks")
public class TaskController extends BaseController {
    private final TaskService taskService;
    private final SubProjectService subProjectService;

    // Loads this on program start
    public TaskController(TaskService taskService,
                          SubProjectService subProjectService,
                          ProjectService projectService) {
        super(subProjectService, projectService);
        this.subProjectService = subProjectService;
        this.taskService = taskService;
    }

    // Read all tasks
    @GetMapping("/all")
    public String readAll(@PathVariable int subprojectId,
            Model model) {
        // Adds all task
        model.addAttribute("tasks", taskService.getTasksBySubProjectID(subprojectId));
        return "task-all";
    }

    @GetMapping("{id}/edit")
    public String readSelected(@PathVariable int id,
                               Model model) {
        if (id < 0) throw new IllegalArgumentException("Noget galt med id");

        Task task = taskService.readSelected(id);
        model.addAttribute("task", task);
        getSubProjectUsersPriority(model);

        return "task-selected";
    }

    @GetMapping("new")
    public String getCreateTaskPage(Model model) {
        model.addAttribute("task", new Task());
        getSubProjectUsersPriority(model);
        return "task-new";
    }

    @PostMapping("create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @PathVariable int subprojectId,
                             @PathVariable int projectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Noget galt med task.");
        // Checks if subproject is set, if not, redirect to subproject page
        if (model.getAttribute("subproject") != null) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);

            return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/new";
        }

        task.setSubProject(new SubProject(subprojectId));
        taskService.create(task, userIDs);
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @PostMapping("{id}/delete")
    public String deleteTask(@PathVariable int id,
                             @PathVariable int subprojectId,
                             @PathVariable int projectId) {
        if (id <= 0) throw new IllegalArgumentException("Noget galt med id.");

        taskService.delete(id);
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @PostMapping("update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @PathVariable int subprojectId,
                             @PathVariable int projectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Noget galt med task.");
        // Checks if subproject is set, if not, redirect to subproject page
        if (model.getAttribute("subproject") != null) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);
            model.addAttribute("error", "Obligatorisk felt her.");
            return "redirect:/tasks/" + task.getId() + "/task";
        }

        task.setSubProject(new SubProject(subprojectId));
        taskService.update(task, userIDs);
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    private void getSubProjectUsersPriority(Model model){
        model.addAttribute("users", taskService.getAllUsers());
        model.addAttribute("priorities", PriorityLevel.values());
    }
}
