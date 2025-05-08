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
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Read all tasks
    @GetMapping
    public String readAll(Model model) {
        // Adds all task
        model.addAttribute("tasks", taskService.readAll());
        return "task-all";
    }

    @GetMapping("{id}/task")
    public String readSelected(@PathVariable int id,
                               Model model) {
        if (id < 0) throw new IllegalArgumentException("Noget galt med id");

        Task task = taskService.readSelected(id);
        model.addAttribute("task", task);
        model.addAttribute("subProjectId", task.getSubProject().getId());
        getSubProjectUsersPriority(model);

        return "task-selected";
    }

    @GetMapping("subProjectId={subprojectId}/new")
    public String getCreateTaskPage(Model model, @PathVariable int subprojectId) {
        int subProjectId = taskService.getSubProjectIdForTask(subprojectId);

        model.addAttribute("task", new Task());
        model.addAttribute("subProjectId", subProjectId);
        getSubProjectUsersPriority(model);
        return "task-new";
    }

    @PostMapping("create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @RequestParam(name = "subProjectId") int subProjectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Noget galt med task.");
        if (subProjectId <= 0) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);
            return "redirect:/tasks/new";
        }

        task.setSubProject(new SubProject(subProjectId));
        taskService.create(task, userIDs);
        return "redirect:/projects/" + subProjectId + "/tasks";
    }

    @PostMapping("{id}/delete")
    public String deleteTask(@PathVariable int id) {
        if (id <= 0) throw new IllegalArgumentException("Noget galt med id.");

        int subProjectId = taskService.delete(id);

        return "redirect:/projects/" + subProjectId + "/tasks";
    }

    @PostMapping("update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) List<Integer> userIDs,
                             @RequestParam(name = "subProjectId") int subProjectId,
                             Model model) {
        if (task == null) throw new IllegalArgumentException("Noget galt med task.");
        if (subProjectId <= 0) {
            getSubProjectUsersPriority(model);
            model.addAttribute("task", task);
            model.addAttribute("error", "Obligatorisk felt her.");
            return "redirect:/tasks/" + task.getId() + "/task";
        }

        task.setSubProject(new SubProject(subProjectId));
        taskService.update(task, userIDs);
        return "redirect:/projects/" + subProjectId + "/tasks";
    }

    private void getSubProjectUsersPriority(Model model){
        model.addAttribute("users", taskService.getAllUsers());
        model.addAttribute("priorities", PriorityLevel.values());
    }
}
