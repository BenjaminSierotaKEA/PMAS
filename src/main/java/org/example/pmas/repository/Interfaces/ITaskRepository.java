package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Task;

import java.util.List;
import java.util.Set;

public interface ITaskRepository extends CrudInterface<Task> {
    void removeUsersFromUserTasks(int taskId, Set<Integer> userIds);
    void addUsersToUserTasks(int taskId, Set<Integer> userIds);
    List<Integer> getCurrentUserIdsFromUserTasks(int taskId);
    List<Task> readAll();

    List<Task> getTasksBySubProjectID(int subProjectId);

    List<Task> findAllByUserId(int userId);
}
