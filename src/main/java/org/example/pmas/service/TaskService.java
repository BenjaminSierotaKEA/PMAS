package org.example.pmas.service;

import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.example.pmas.service.comparators.TaskDeadlineComparator;
import org.example.pmas.service.comparators.TaskPriorityComparator;
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

    public void create(Task task, List<Integer> userIDs) {
        Task createdTask = taskRepository.create(task);
        if (createdTask == null) throw new NotFoundException(task.getId());

        // Adds user and task to junction table if any
        if (userIDs != null && !userIDs.isEmpty())
            addUserToTask(createdTask.getId(), userIDs);
    }

    public List<Task> readAll() {
        List<Task> allTask = taskRepository.readAll();
        if (allTask == null) return Collections.emptyList();

        allTask.sort(new TaskDeadlineComparator()
                // priority wil be sorted high -> low because of reverse
                .thenComparing(new TaskPriorityComparator().reversed()));
        return allTask;
    }

    public Task readSelected(int id) {
        // gets the task and check if it exists.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new NotFoundException(id);

        return task;
    }

    public List<SubProject> getAllSubproject() {
        return subProjectRepository.readAll();
    }

    public void delete(int id) {
        // check if id exist.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new NotFoundException(id);

        taskRepository.delete(id);
    }

    public void update(Task task, List<Integer> userIDs) {
        // Check if id exist.
        var old = taskRepository.readSelected(task.getId());
        if (old == null) throw new NotFoundException(task.getId());

        boolean succes = taskRepository.update(task);
        if (!succes) throw new NotFoundException("Id:" + task.getId() + " Kunne ikke opdatere task");

        // Adds users to the junction table if any
        if (userIDs != null && !userIDs.isEmpty())
            addUserToTask(task.getId(), userIDs);
    }

    private void addUserToTask(int taskId, List<Integer> newUserIds) {
        // Fetch users for comparison
        List<Integer> currentUserIds = taskRepository.getCurrentUserIdsFromUserTasks(taskId);

        // Check differences for add or remove user from a task
        Set<Integer> toAdd = differenceOrEmpty(newUserIds, currentUserIds);
        Set<Integer> toRemove = differenceOrEmpty(currentUserIds, newUserIds);

        // Add/Remove if needed
        taskRepository.addUsersToUserTasks(taskId, toAdd);
        taskRepository.removeUsersFromUserTasks(taskId, toRemove);
    }

    // if no users added to the list. it will throw and error.
    // this will avoid error
    private Set<Integer> differenceOrEmpty(List<Integer> baseList, List<Integer> subtractList) {
        // We've to check null and isEmpty or else either update or create won't work.
        if (subtractList == null || subtractList.isEmpty()) subtractList = Collections.emptyList();
        if (baseList == null || baseList.isEmpty()) baseList = Collections.emptyList();

        Set<Integer> result = new HashSet<>(baseList);
        result.removeAll(subtractList);
        return result;
    }

    public List<User> getAllUsers() {
        return userRepository.readAll();
    }
}
