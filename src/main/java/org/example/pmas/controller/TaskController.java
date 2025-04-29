package org.example.pmas.controller;

import org.example.pmas.service.TaskService;

public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

}
