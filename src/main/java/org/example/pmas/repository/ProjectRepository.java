package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository implements org.example.pmas.repository.Interfaces.IProjectRepository {

    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public Project create(Project project) {
        System.out.println("Hello from the create in the repo");
        String sql = "USE pmasdatabase; INSERT INTO projects(name, description, timeBudget, deadline) VALUES(?,?,?,?)";
        try{
            jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getTimeBudget(), project.getDeadline());
        }catch(DataAccessException e){
            throw new RuntimeException("Couldnt add " + project.getName(), e);
        }
        return null;
    }

    @Override
    public List<Project> readAll() {
        return List.of();
    }

    @Override
    public Project readSelected() {
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
