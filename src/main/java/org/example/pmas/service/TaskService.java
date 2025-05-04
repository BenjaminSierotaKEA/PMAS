package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ISubProjectRepository subProjectRepository;

    public TaskService(ITaskRepository taskRepository, ISubProjectRepository subProjectRepository) {
        this.taskRepository = taskRepository;
        this.subProjectRepository = subProjectRepository;
    }

    public boolean create(Task task, List<Integer> userIDs) {
        if(userIDs != null)
            task.setUsers(userIDToUser(userIDs));

        Task createdTask = taskRepository.create(task);
        return createdTask != null && createdTask.getId() > 0;
    }

    private Set<User> userIDToUser(List<Integer> userIDs){
        Set<User> users = new HashSet<>();

        for (Integer userID : userIDs) {
            users.add(new User(userID));
        }
        return users;
    }

    public List<Task> readAll() {
        return taskRepository.readAll();
    }

    public Task readSelected(int id){
        var task = taskRepository.readSelected(id);
        if(task == null) throw new WrongInputException("Der er noget galt med id.");

        return task;
    }

    public List<SubProject> getAllSubproject(){
        return subProjectRepository.readAll();
    }

    public void delete(int id){
        var task = taskRepository.readSelected(id);
        if(task == null) throw new WrongInputException("Der noget galt med id.");

        taskRepository.delete(id);
    }
}
