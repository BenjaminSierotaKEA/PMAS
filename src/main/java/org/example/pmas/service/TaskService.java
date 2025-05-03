package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ISubProjectRepository subProjectRepository;

    public TaskService(ITaskRepository taskRepository, ISubProjectRepository subProjectRepository) {
        this.taskRepository = taskRepository;
        this.subProjectRepository = subProjectRepository;
    }

    public boolean create(Task task) {
        Task createdTask = taskRepository.create(task);
        return createdTask != null && createdTask.getId() > 0;
    }

    public List<Task> readAll() {
        return taskRepository.readAll();
    }

    public Task readSelected(int id){
        var Task = taskRepository.readSelected(id);
        if(Task == null) throw new WrongInputException("Noget gik galt. Id findes ikke.");

        return Task;
    }

    public List<SubProject> getAllSubproject(){
        return subProjectRepository.readAll();
    }
}
