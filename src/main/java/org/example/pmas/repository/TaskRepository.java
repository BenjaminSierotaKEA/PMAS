package org.example.pmas.repository;

import org.example.pmas.exception.ConnectionException;
import org.example.pmas.model.Task;
import org.example.pmas.model.rowMapper.TaskRowMapper;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository implements ITaskRepository {
    private final JdbcTemplate jdbcTemplate;


    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public List<Task> readAll() throws ConnectionException {
        String sql =
                "SELECT " +
                        "t.id, " +
                        "t.name, " +
                        "t.completed, " +
                        "t.description, " +
                        "t.timeBudget, " +
                        "t.timeTaken, " +
                        "GROUP_CONCAT(u.id) AS user_ids, " +
                        "GROUP_CONCAT(u.name) AS user_names " +
                        "FROM tasks t " +
                        "JOIN usertasks ut ON t.id = ut.taskid " +
                        "JOIN users u ON ut.userid = u.id " +
                        "GROUP BY t.id, t.name, t.completed, t.description, t.timeBudget, t.timeTaken";

        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Task readSelected(int id) throws ConnectionException {
        String sql = " SELECT " +
                "t.id, " +
                "t.name, " +
                "t.completed, " +
                "t.description, " +
                "t.timeBudget, " +
                "t.timeTaken, " +
                "sp.name as subproject_name, " +
                "GROUP_CONCAT(u.id) as user_ids, " +
                "GROUP_CONCAT(u.name) as user_names " +
                "FROM tasks t " +
                "JOIN usertasks ut ON t.id = ut.taskid " +
                "JOIN users u ON ut.userid = u.id " +
                "JOIN subprojects sp ON sp.id = t.id " +
                "WHERE t.id = ? " +
                "GROUP BY t.id, t.name, t.completed, t.description, t.timeBudget, t.timeTaken";

        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Object oldObject, Object newObject) {
        return false;
    }
}
