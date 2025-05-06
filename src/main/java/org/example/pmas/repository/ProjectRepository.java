package org.example.pmas.repository;

import org.example.pmas.model.Project;
import org.example.pmas.model.rowMapper.ProjectRowMapper;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.springframework.dao.DataAccessException;
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
        String sql = "INSERT INTO projects(name, description, timeBudget, deadline) VALUES(?,?,?,?)";
        try{
            jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getTimeBudget(), project.getDeadline());
        }catch(DataAccessException e){
            throw new RuntimeException("Couldnt add " + project.getName(), e);
        }
        return null;
    }

    @Override
    public List<Project> readAll() {
        String sql = "SELECT * FROM projects";
        try{
            return jdbcTemplate.query(sql, new ProjectRowMapper());
        }catch (DataAccessException e){
            throw new RuntimeException("Couldnt read all projects", e);
        }

    }

    @Override
    public Project readSelected(int id) {
        String sql = "SELECT * FROM projects WHERE projects.id=?";
        try{
            //query returns a list, we get the zeroth item on it to return as a single project
            return jdbcTemplate.query(sql, new ProjectRowMapper(), id).get(0);
        }catch(DataAccessException e){
            throw new RuntimeException("couldnt find project where id=" + id, e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM projects where projects.id = ?";
        try{
            jdbcTemplate.update(sql, id);
            return true;
        }catch(DataAccessException e){
            throw new RuntimeException("Could not delete project: ID=" + id, e);
        }

    }

    @Override
    public boolean update(Project newProject) {


        String sql = "UPDATE projects SET name = ?, description = ?, timebudget = ?, deadline = ? WHERE id=?";
        try{
            jdbcTemplate.update(sql,
                    newProject.getName(),
                    newProject.getDescription(),
                    newProject.getTimeBudget(),
                    newProject.getDeadline(),
                    newProject.getId());
        }catch(DataAccessException e){
            throw new RuntimeException("Could not update project: " + newProject.getName(), e);
        }


        return false;
    }

    public boolean doesProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
    }
}
