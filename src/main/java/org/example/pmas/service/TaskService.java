package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
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

    public Task readSelected(int id){
        var Task = taskRepository.readSelected();
        if(Task == null) throw new WrongInputException("Task blev ikke fundet");

        return Task;
    }
}
