package org.example.pmas.repository;

import org.example.pmas.model.dto.ProjectDTO;
import org.example.pmas.exception.DatabaseException;
import org.example.pmas.model.Project;
import org.example.pmas.model.dto.rowMapperDTO.ProjectDTORowMapper;
import org.example.pmas.model.rowMapper.ProjectRowMapper;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public class ProjectRepository implements IProjectRepository {

    private final JdbcTemplate jdbcTemplate;


    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Project create(Project project) {
        String sql = "INSERT INTO projects(name, description, timeBudget, deadline, completed) VALUES(?,?,?,?,?)";
        String retrievalSql = "SELECT * FROM projects WHERE name = ?";
        Project returnProject = null;
        try {
            jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getTimeBudget(), project.getDeadline(), project.isCompleted());
            returnProject = jdbcTemplate.query(retrievalSql, new ProjectRowMapper(), project.getName()).get(0);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
        return returnProject;
    }

    @Override
    public List<Project> readAll() {
        String sql = "SELECT * FROM projects";
        try {
            return jdbcTemplate.query(sql, new ProjectRowMapper());
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }

    }

    @Override
    public List<Project> readProjectsOfUser(int userID) {
        String sql = "SELECT p.*\n" +
                "FROM projects p\n" +
                "JOIN userprojects up ON p.id = up.projectid\n" +
                "WHERE up.userid = ?;";
        try {
            return jdbcTemplate.query(sql, new ProjectRowMapper(), userID);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error: Couldn't read projects for user id " + userID, e);
        }
    }

    @Override
    public Project readSelected(int id) {
        String sql = "SELECT * FROM projects WHERE projects.id=?";
        try {
            //query returns a list, we get the zeroth item on it to return as a single project
            return jdbcTemplate.query(sql, new ProjectRowMapper(), id).get(0);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM projects where projects.id = ?";
        try {
            jdbcTemplate.update(sql, id);
            return true;
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }

    }

    @Override
    public boolean update(Project newProject) {
        String sql = "UPDATE projects SET name = ?, description = ?, timebudget = ?, deadline = ? WHERE id=?";
        try {
            return jdbcTemplate.update(sql,
                    newProject.getName(),
                    newProject.getDescription(),
                    newProject.getTimeBudget(),
                    newProject.getDeadline(),
                    newProject.getId()) > 0;
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    public boolean doesProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
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
                "SUM(IF(sp.completed IS TRUE, 1, 0)) AS completedSubProjects, " +
                "SUM(IF(sp.completed = true, sp.timeBudget, 0)) AS timeTaken " +
                "FROM userprojects up " +
                "JOIN projects p ON up.projectid = p.id " +
                "LEFT JOIN subprojects sp ON sp.projectID = p.id " +
                "WHERE up.userid = ? " +
                "GROUP BY  p.id, p.name, p.description, p.timeBudget, p.completed";
        try {
            return jdbcTemplate.query(sql, new ProjectDTORowMapper(), userId);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error: Could not get all subprojects with id: " + userId, e);
        }
    }

    @Transactional
    @Override
    public void addUsersToProject(int projectID, Set<Integer> userIDs) {
        String sql = "INSERT INTO userprojects(projectid, userid) VALUES (?,?)";

        for (Integer i : userIDs) {
            try {
                jdbcTemplate.update(sql, projectID, i);
            } catch (DataAccessException e) {
                throw new DatabaseException("Database error: could not insert userid associated with Project.", e);
            }
        }


    }

    @Transactional
    @Override
    public void removeUsersFromProject(int projectID, Set<Integer> userIDs) {
        //see addUsersToProject for an explanation of what is going on here

        String sql = "DELETE FROM userprojects WHERE projectid = ? AND userid = ?";


        for (Integer i : userIDs) {
            try {
                jdbcTemplate.update(sql, projectID, i);
            } catch (DataAccessException e) {
                throw new DatabaseException("Database error: could not remove userID associated with project", e);
            }
        }
    }

    @Override
    public void updateProjectCompleted(int projectID, boolean completed) {
        String sql = "UPDATE projects SET completed = ? " +
                "WHERE id = ?";
        try {
            jdbcTemplate.update(sql, completed, projectID);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean checkProjectName(String name) {
        String sql = "SELECT EXISTS (SELECT 1 FROM projects WHERE name = ?)";

        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{name}, Boolean.class));
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public int getProjectIDOfUsersSubproject(int userID, int subprojectID) {
        String sql = """        
                SELECT sp.projectID        
                FROM usertasks ut        
                JOIN tasks t ON ut.taskID = t.id        
                JOIN subprojects sp ON t.subProjectID = sp.id        
                WHERE ut.userID = ? AND sp.id = ?        
                LIMIT 1    """;
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, userID, subprojectID);
        return result != null ? result : 0;
    }


}
