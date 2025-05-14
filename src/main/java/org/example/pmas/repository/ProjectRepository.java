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

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                "p.timeTaken    , " +
                "p.deadline, " +
                "COUNT(sp.id) AS totalSubProjects, " +
                "SUM(CASE WHEN sp.completed = true THEN 1 ELSE 0 END) AS completedSubProjects, " +
                "SUM(CASE WHEN sp.completed = true THEN sp.timeTaken ELSE 0 END) AS timeTaken, " +
                "SUM(sp.timeBudget) AS timeBudget " +
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

    @Override
    public void addUsersToProject(int projectID,List<Integer> userIDs){
            //WIP
            String sql = "INSERT INTO userprojects(projectid, userid) VALUES (?,?)";


            //this is a use of batchupdate, which allows us to update multiple rows of the
            //table with a single database query. first, the sql statement is passed as the first argument,
            //than an inline implementation of the batchpreparedstatementsetter interface, containing two functions
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                //first is the setvalues function, which  sets the values of the question marks in the
                //sql statement for each insertion. i is the index of the iteration through the batch
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {

                    //ps.setInt sets the value of one of the question marks in the statement. the first
                    //argument is which question mark in the statement is being set, the second is its value
                    ps.setInt(1, projectID);
                    ps.setInt(2, userIDs.get(i));
                }

                //This specifies the size of the batch.
                @Override
                public int getBatchSize() {
                    return userIDs.size();
                }
            });
    }

    @Override
    public void removeUsersFromProject(int projectID, List<Integer> userIDs){
        //see addUsersToProject for an explanation of what is going on here

        String sql = "DELETE FROM userprojects WHERE projectid = ? AND userid = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, projectID);
                ps.setInt(2, userIDs.get(i));
            }

            @Override
            public int getBatchSize() {
                return userIDs.size();
            }
        });


    }


}
