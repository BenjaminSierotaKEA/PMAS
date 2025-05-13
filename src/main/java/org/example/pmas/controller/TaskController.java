package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.enums.PriorityLevel;
import org.example.pmas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("projects/{projectId}/subprojects/{subprojectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    // Loads this on program start
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Read all tasks
    @GetMapping("/all")
    public String readAll(@PathVariable(value = "projectId") int projectId,
                          @PathVariable(value = "subprojectId") int subprojectId,
            Model model) {
        // Adds all task to HTML
        model.addAttribute("tasks", taskService.getTasksBySubProjectID(subprojectId));
        model.addAttribute("subproject", taskService.getSubProject(subprojectId));
        return "task-all";
    }

    @GetMapping("{id}/edit")
    public String readSelected(@PathVariable(value = "id") int id,
                               @PathVariable(value = "projectId") int projectId,
                               @PathVariable(value = "subprojectId") int subprojectId,
                               Model model) {
        if (id < 0) throw new IllegalArgumentException("Something wrong with id");

        Task task = taskService.readSelected(id);
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("task", task);
        getSubProjectUsersPriority(model);

        return "task-update";
    }

    @GetMapping("new")
    public String getCreateTaskPage(@PathVariable(value = "projectId") int projectId,
                                    @PathVariable(value = "subprojectId") int subprojectId,
                                    Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("projectId", projectId);
        getSubProjectUsersPriority(model);
        return "task-new";
    }

    @PostMapping("create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Something wrong with task.");
        // Checks if subproject is set, if not, redirect to subproject page
        if (subprojectId <= 0 || projectId <= 0) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);

            return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/new";
        }

        task.setSubProject(new SubProject(subprojectId));
        taskService.create(task, userIDs);
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @PostMapping("{id}/delete")
    public String deleteTask(@PathVariable(value = "id") int id,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId) {
        if (id <= 0) throw new IllegalArgumentException("Noget galt med id.");

        taskService.delete(id);
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @PostMapping("update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Something wrong with task.");
        // Checks if subproject is set, if not, redirect to subproject page
        if (subprojectId <= 0 || projectId <= 0 || task.getId() <= 0) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);
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
