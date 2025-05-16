package org.example.pmas.service;

import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.exception.UpdateObjectException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.example.pmas.util.SortList;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;
    private final ISubProjectRepository subProjectRepository;

    public TaskService(ITaskRepository taskRepository,
                       IUserRepository userRepository,
                       ISubProjectRepository subProjectService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.subProjectRepository = subProjectService;
    }

    public void create(Task task, List<Integer> userIDs) {
        if(!subProjectRepository.doesSubProjectExist(task.getSubProject().getId()))
            throw new NotFoundException(task.getSubProject().getId());

        Task createdTask = taskRepository.create(task);
        if (createdTask == null) throw new NotFoundException(task.getId());

        // Adds user and task to the junction table if any
        if (userIDs != null)
            addUserToTask(createdTask.getId(), userIDs);
    }

    public List<Task> readAll() {
        List<Task> allTask = taskRepository.readAll();

        return SortList.task(allTask);
    }

    public Task readSelected(int id) {
        // gets the task and check if it exists.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new NotFoundException(id);

        return task;
    }

    public void delete(int id) {
        // check if id exist.
        var task = taskRepository.readSelected(id);
        if (task == null) throw new NotFoundException(id);

        // Skal tænkes igennem igen
        if (!taskRepository.delete(id))
            throw new DeleteObjectException(id);
    }

    public void update(Task task, List<Integer> userIDs) {
        // Check if id exist.
        var old = taskRepository.readSelected(task.getId());
        if (old == null) throw new NotFoundException(task.getId());

        if (!taskRepository.update(task))
            throw new UpdateObjectException(task.getId());

        // Adds users to the junction table if any
        addUserToTask(task.getId(), userIDs);
    }

    public List<Task> getTasksBySubProjectID(int subProjectId) {
        List<Task> taskList = taskRepository.getTasksBySubProjectID(subProjectId);

        return SortList.task(taskList);
    }

    // This handle the junction table relation
    private void addUserToTask(int taskId, List<Integer> newUserIds) {
        // Get users for comparison
        List<Integer> currentUserIds = taskRepository.getCurrentUserIdsFromUserTasks(taskId);

        // Check differences for add or remove user from a task
        Set<Integer> toAdd = differenceOrEmpty(newUserIds, currentUserIds);
        Set<Integer> toRemove = differenceOrEmpty(currentUserIds, newUserIds);

        // Add/Remove if needed
        taskRepository.addUsersToUserTasks(taskId, toAdd);
        taskRepository.removeUsersFromUserTasks(taskId, toRemove);
    }

    // Filter users who've been deleted or added
    private Set<Integer> differenceOrEmpty(List<Integer> baseList, List<Integer> subtractList) {
        // We've to check null and isEmpty or else either update or create won't work. If a list is empty
        if (subtractList == null || subtractList.isEmpty()) subtractList = Collections.emptyList();
        if (baseList == null || baseList.isEmpty()) baseList = Collections.emptyList();

        // Adds the list with values
        Set<Integer> result = new HashSet<>(baseList);
        // Removes duplicates and returns
        result.removeAll(subtractList);
        return result;
    }

    public List<User> getAllUsersOnProject(int projectId) {
        return userRepository.getAllOnProject(projectId);
    }

    public SubProject getSubProject(int id) {
        var subproject = subProjectRepository.readSelected(id);
        if (subproject == null) {
            throw new NotFoundException(id);
        }
        return subproject;
    }


}
