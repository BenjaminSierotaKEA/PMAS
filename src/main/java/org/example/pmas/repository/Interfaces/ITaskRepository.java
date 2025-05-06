package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Task;

import java.util.List;

public interface ITaskRepository extends CrudInterface<Task> {
    List<Task> getTasksBySubProjectID(int subProjectId);
    boolean addUserToTask(int taskid, List<Integer> userids);
}
