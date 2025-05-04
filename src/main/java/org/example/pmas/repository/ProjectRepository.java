package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepository implements IProjectRepository {

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
    public boolean update(Project newObject) {
        return false;
    }

    public boolean doesProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
    }
}
