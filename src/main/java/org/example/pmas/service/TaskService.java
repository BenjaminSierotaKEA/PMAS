package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
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

    public boolean create(Task task, List<Integer> userIDs) {
        Task createdTask = taskRepository.create(task);
        if (createdTask == null) return false;

        // Adds user and task to junction table
        return taskRepository.addUserToTask(createdTask.getId(), userIDs);
    }

    public List<Task> readAll() {
        return taskRepository.readAll();
    }

    public Task readSelected(int id) {
        // gets the task and check if it exists.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new WrongInputException("Der er noget galt med id.");

        return task;
    }

    public List<SubProject> getAllSubproject() {
        return subProjectRepository.readAll();
    }

    public void delete(int id) {
        // check if id exist.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new WrongInputException("Der noget galt med id.");

        taskRepository.delete(id);
    }

    public boolean update(Task task, List<Integer> userIDs) {
        // Check if id exist.
        var old = taskRepository.readSelected(task.getId());
        if (old == null) throw new WrongInputException("Der er noget galt med id.");

        boolean succes = taskRepository.update(task);
        if (!succes) return false;

        // Adds users to the junction table
        return taskRepository.addUserToTask(task.getId(), userIDs);
    }
}
