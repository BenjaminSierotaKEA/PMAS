package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProjectRepository implements org.example.pmas.repository.Interfaces.IProjectRepository {

    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public Project create(Project project) {
        return null;
    }

    @Override
    public List<Project> readAll() {
        return List.of();
    }

    @Override
    public Project readSelected(int id) {
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
