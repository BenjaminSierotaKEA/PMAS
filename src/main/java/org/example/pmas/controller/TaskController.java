package org.example.pmas.controller;

import org.example.pmas.model.Task;
import org.example.pmas.model.User;
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
    public String readSelected(@PathVariable int id, Model model) {
        if(id < 0) throw new IllegalArgumentException("Id skal være positivt");

        model.addAttribute("task", taskService.readSelected(id));
        return "task-selected";
    }

    @GetMapping("new")
    public String getCreateTaskPage(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("subprojects", taskService.getAllSubproject());
        model.addAttribute("users", List.of(
                new User(1,"Rebecca black"),
                new User(2,"John Smith"),
                new User(3,"CharlieXcX")
                ));
        return "task-new";
    }

    @PostMapping("create")
    public String createTask(@ModelAttribute Task task, Model model){
        if(task == null || task.getSubProject().getId() <= 0) throw new IllegalArgumentException("Du skal udfylde obligatoriske felter");

        boolean success = taskService.create(task);
        if (!success) {
            model.addAttribute("error", "Opgaven kunne ikke oprettes.");
            return "task-new";
        }

        return "redirect:/tasks";
    }
}
