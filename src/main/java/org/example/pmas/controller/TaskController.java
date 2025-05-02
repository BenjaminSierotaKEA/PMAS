package org.example.pmas.controller;

import org.example.pmas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "task";
    }

    @GetMapping("{id}/task")
    public String readSelected(@PathVariable int id, Model model) {
        if(id < 0) throw new IllegalArgumentException("Id skal være positivt");

        model.addAttribute("task", taskService.readSelected(id));
        return "task-selected";
    }

}
