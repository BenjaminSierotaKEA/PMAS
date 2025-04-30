package org.example.pmas.repository;

import org.example.pmas.model.Task;
import org.example.pmas.repository.Interfaces.CrudInterface;
import org.example.pmas.repository.Interfaces.TaskRepositoryInterface;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TaskRepository implements TaskRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;


    public TaskRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public List<Task> readAll() {
        return List.of();
    }

    @Override
    public Task readSelected() {
        return null;
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
