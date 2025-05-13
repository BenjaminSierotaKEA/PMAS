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
import org.example.pmas.service.comparators.TaskDeadlineComparator;
import org.example.pmas.service.comparators.TaskPriorityComparator;
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
        Task createdTask = taskRepository.create(task);
        if (createdTask == null) throw new NotFoundException(task.getId());

        // Adds user and task to junction table if any
        if (userIDs != null)
            addUserToTask(createdTask.getId(), userIDs);
    }

    public List<Task> readAll() {
        List<Task> allTask = taskRepository.readAll();
        if (allTask == null) return Collections.emptyList();

        // Sorts the list by deadline and priority.
        // Now the list isn't immutable, so we can modify it.
        List<Task> modifiableList = new ArrayList<>(allTask);
        modifiableList.sort(new TaskPriorityComparator().reversed()
                // priority wil be sorted high -> low because of reverse
                .thenComparing(new TaskDeadlineComparator()));
        return allTask;
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
        if (task == null) throw new NotFoundException("Task didnt exist with id: " + id);

        // Skal tænkes igennem igen
        if (!taskRepository.delete(id))
            throw new DeleteObjectException("Id:" + id + " could not be deleted from database.");
    }

    public void update(Task task, List<Integer> userIDs) {
        // Check if id exist.
        var old = taskRepository.readSelected(task.getId());
        if (old == null) throw new NotFoundException(task.getId());

        if (!taskRepository.update(task))
            throw new UpdateObjectException("Id:" + task.getId() + " could not be updated in database.");

        // Adds users to the junction table if any
        addUserToTask(task.getId(), userIDs);
    }

    public List<Task> getTasksBySubProjectID(int subProjectId) {
        return taskRepository.getTasksBySubProjectID(subProjectId);
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

    private Set<Integer> differenceOrEmpty(List<Integer> baseList, List<Integer> subtractList) {
        // We've to check null and isEmpty or else either update or create won't work.
        if (subtractList == null || subtractList.isEmpty()) subtractList = Collections.emptyList();
        if (baseList == null || baseList.isEmpty()) baseList = Collections.emptyList();

        // Adds the list with values
        Set<Integer> result = new HashSet<>(baseList);
        // Removes duplicates and returns
        result.removeAll(subtractList);
        return result;
    }

    public List<User> getAllUsers() {
        return userRepository.readAll();
    }

    public SubProject getSubProject(int id) {
        var subproject = subProjectRepository.readSelected(id);
        if(subproject == null) {
            throw new NotFoundException(id);
        }
        return subproject;
    }

}
