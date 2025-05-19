package org.example.pmas.service;

import org.example.pmas.exception.CreateObjectException;
import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.exception.UpdateObjectException;
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
                       ISubProjectRepository subProjectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.subProjectRepository = subProjectRepository;
    }

    public void create(Task task, Set<Integer> userIDs) {
        if(!subProjectRepository.doesSubProjectExist(task.getSubProject().getId()))
            throw new NotFoundException(task.getSubProject().getId());

        Task createdTask = taskRepository.create(task);
        if (createdTask == null) throw new CreateObjectException(task.getId());

        // Adds user and task to the junction table if any
        if (userIDs != null)
            addUserToTask(createdTask.getId(), userIDs);
    }

    public List<Task> readAll() {
        List<Task> allTask = taskRepository.readAll();

        return SortList.tasksDeadlinePriority(allTask);
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

    public void update(Task task, Set<Integer> userIDs) {
        // Check if id exist.
        var old = taskRepository.readSelected(task.getId());
        if (old == null) throw new NotFoundException(task.getId());

        if (!taskRepository.update(task))
            throw new UpdateObjectException(task.getId());

        // Adds users to the junction table if any
        addUserToTask(task.getId(), userIDs);
    }

    public List<Task> getTasksBySubProjectID(int subProjectId) {
        if(!subProjectRepository.doesSubProjectExist(subProjectId))
            throw new NotFoundException(subProjectId);

        List<Task> taskList = taskRepository.getTasksBySubProjectID(subProjectId);

        return SortList.tasksDeadlinePriority(taskList);
    }

    // Handle the junction table relation
    private void addUserToTask(int taskId, Set<Integer> newUserIds) {
        // Get users for comparison
        List<Integer> currentUserIds = taskRepository.getCurrentUserIdsFromUserTasks(taskId);

        // Check differences for add or remove user from a task
        Set<Integer> toAdd = differenceOrEmpty(newUserIds, currentUserIds);
        Set<Integer> toRemove = differenceOrEmpty(new HashSet<>(currentUserIds), newUserIds);

        // Add/Remove if needed
        taskRepository.addUsersToUserTasks(taskId, toAdd);
        taskRepository.removeUsersFromUserTasks(taskId, toRemove);
    }

    // Filter users who've been deleted or added
    private Set<Integer> differenceOrEmpty(Set<Integer> baseSet, Collection<Integer> subtractCollection) {
        // We've to check null and isEmpty or else either update or create won't work. If a list is empty
        if (subtractCollection == null || subtractCollection.isEmpty()) subtractCollection = Collections.emptySet();
        if (baseSet == null || baseSet.isEmpty()) baseSet = Collections.emptySet();

        // Adds the list with values
        Set<Integer> result = new HashSet<>(baseSet);
        // Removes duplicates and returns
        result.removeAll(subtractCollection);
        return result;
    }

    public List<User> getAllUsersOnProject(int projectId) {
        List<User> users = userRepository.getAllOnProject(projectId);

        return SortList.userName(users);
    }
}
