package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ITaskRepository extends CrudInterface<Task> {
    int removeUsersFromUserTasks(int taskId, Set<Integer> userIds);
    int addUsersToUserTasks(int taskId, Set<Integer> userIds);
    List<Integer> getCurrentUserIdsFromUserTasks(int taskId);

    List<Task> getTasksBySubProjectID(int subProjectId);

    @Transactional
    List<Task> findAllByUserId(int userId);
}
