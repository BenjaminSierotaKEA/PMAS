package org.example.pmas.service;

import org.example.pmas.model.Task;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final ITaskRepository taskRepository;


    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> readAll() {
        return taskRepository.readAll();
    }
}
