package org.example.pmas.service;

import org.example.pmas.repository.TaskRepository;

public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }


}
