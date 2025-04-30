package org.example.pmas.service;

import org.example.pmas.repository.Interfaces.ITaskRepository;

public class TaskService {

    private final ITaskRepository taskRepository;


    public TaskService(ITaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }


}
