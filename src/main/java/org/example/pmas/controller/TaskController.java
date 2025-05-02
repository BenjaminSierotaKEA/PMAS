package org.example.pmas.controller;

import org.example.pmas.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String readAll(Model model) {
        // Adds all task
        model.addAttribute("tasks", taskService.readAll());
        return "task";
    }

}
