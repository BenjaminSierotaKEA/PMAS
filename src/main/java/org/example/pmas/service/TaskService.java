package org.example.pmas.service;

import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ISubProjectRepository subProjectRepository;
    private final IUserRepository userRepository;

    public TaskService(ITaskRepository taskRepository, ISubProjectRepository subProjectRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.subProjectRepository = subProjectRepository;
        this.userRepository = userRepository;
    }

    public boolean create(Task task, List<Integer> userIDs) {
        Task createdTask = taskRepository.create(task);
        if (createdTask == null) return false;

        // Adds user and task to junction table
        return addUserToTask(createdTask.getId(), userIDs);
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
        return addUserToTask(task.getId(), userIDs);
    }

    private boolean addUserToTask(int taskId, List<Integer> newUserIds) {
        // Fetch users for comparison
        List<Integer> currentUserIds = taskRepository.getCurrentUserIdsFromUserTasks(taskId);

        // Check differences for add or remove user from a task
        Set<Integer> toAdd = differenceOrEmpty(newUserIds, currentUserIds);
        Set<Integer> toRemove = differenceOrEmpty(currentUserIds, newUserIds);

        // Add/Remove if needed
        int added = taskRepository.addUsersToUserTasks(taskId, toAdd);
        int removed = taskRepository.removeUsersFromUserTasks(taskId, toRemove);

        return added == 0 && removed == 0;
    }
    // if no users added to the list. it will throw and error.
    // this will avoid error
    private Set<Integer> differenceOrEmpty(List<Integer> baseList, List<Integer> subtractList) {
        // We've to check null and isEmpty or else either update or create won't work.
        if (subtractList == null || subtractList.isEmpty()) subtractList = Collections.emptyList();
        if (baseList == null || subtractList.isEmpty()) baseList = Collections.emptyList();

        Set<Integer> result = new HashSet<>(baseList);
        result.removeAll(subtractList);
        return result;
    }

    public List<User> getAllUsers(){
        return userRepository.readAll();
    }
}
