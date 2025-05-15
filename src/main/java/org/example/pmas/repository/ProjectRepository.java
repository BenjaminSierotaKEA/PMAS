package org.example.pmas.repository;

import org.example.pmas.dto.ProjectDTO;
import org.example.pmas.exception.DatabaseException;
import org.example.pmas.model.Project;
import org.example.pmas.model.rowMapper.ProjectDTORowMapper;
import org.example.pmas.model.rowMapper.ProjectRowMapper;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public class ProjectRepository implements IProjectRepository {

    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public Project create(Project project) {
        String sql = "INSERT INTO projects(name, description, timeBudget, deadline) VALUES(?,?,?,?)";
        String retrievalSql = "SELECT * FROM projects WHERE name = ?";
        Project returnProject = null;
        try{
            jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getTimeBudget(), project.getDeadline());
            returnProject = jdbcTemplate.query(retrievalSql, new ProjectRowMapper(), project.getName()).get(0);
        }catch(DataAccessException e){
            throw new DatabaseException("Couldnt add " + project.getName(), e);
        }
        return returnProject;
    }

    @Override
    public List<Project> readAll() {
        String sql = "SELECT * FROM projects";
        try{
            return jdbcTemplate.query(sql, new ProjectRowMapper());
        }catch (DataAccessException e){
            throw new DatabaseException("Couldnt read all projects", e);
        }

    }

    @Override
    public List<Project> readProjectsOfUser(int userID){
        String sql ="SELECT p.*\n" +
                "FROM projects p\n" +
                "JOIN userprojects up ON p.id = up.projectid\n" +
                "WHERE up.userid = ?;";
        try{
            return jdbcTemplate.query(sql, new ProjectRowMapper(), userID);
        }catch(DataAccessException e){
            throw new DatabaseException("Couldnt read projects for user id " + userID);
        }
    }

    @Override
    public Project readSelected(int id) {
        String sql = "SELECT * FROM projects WHERE projects.id=?";
        try{
            //query returns a list, we get the zeroth item on it to return as a single project
            return jdbcTemplate.query(sql, new ProjectRowMapper(), id).get(0);
        }catch(DataAccessException e){
            throw new DatabaseException("couldnt find project where id=" + id, e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM projects where projects.id = ?";
        try{
            jdbcTemplate.update(sql, id);
            return true;
        }catch(DataAccessException e){
            throw new DatabaseException("Could not delete project: ID=" + id, e);
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
            throw new DatabaseException("Could not update project: " + newProject.getName(), e);
        }


        return false;
    }

    public boolean doesProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
    }

    public List<ProjectDTO> getProjectDTOByUserID(int userId) {
        String sql = "SELECT " +
                "p.id, " +
                "p.name, " +
                "p.description, " +
                "p.timeBudget, " +
                "p.completed, " +
                "p.deadline, " +
                "COUNT(sp.id) AS totalSubProjects, " +
                "SUM(CASE WHEN sp.completed = true THEN 1 ELSE 0 END) AS completedSubProjects, " +
                "SUM(CASE WHEN sp.completed = true THEN sp.timeBudget ELSE 0 END) AS timeTaken " +
                "FROM userprojects up " +
                "JOIN projects p ON up.projectid = p.id " +
                "LEFT JOIN subprojects sp ON sp.projectID = p.id " +
                "WHERE up.userid = ? " +
                "GROUP BY  p.id, p.name, p.description, p.timeBudget, p.timeTaken, p.completed";
        try {
            return jdbcTemplate.query(sql, new ProjectDTORowMapper(),userId);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error: Could not get all subprojects with id: " + userId, e);
        }
    }

    @Transactional
    @Override
    public void addUsersToProject(int projectID,Set<Integer> userIDs){
            String sql = "INSERT INTO userprojects(projectid, userid) VALUES (?,?)";

            for(Integer i : userIDs){
                try{
                    jdbcTemplate.update(sql, projectID, i);
                }catch(DataAccessException e){
                    throw new DatabaseException("Database error: could not insert userid associated with Project.", e);
                }
            }



    }

    @Transactional
    @Override
    public void removeUsersFromProject(int projectID, Set<Integer> userIDs){
        //see addUsersToProject for an explanation of what is going on here

        String sql = "DELETE FROM userprojects WHERE projectid = ? AND userid = ?";


        for(Integer i : userIDs){
            try {
                jdbcTemplate.update(sql, projectID, i);
            }catch (DataAccessException e){
                throw new DatabaseException("Database error: could not remove userID associated with project");
            }

        }


    }


}
