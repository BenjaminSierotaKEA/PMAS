package org.example.pmas.repository;

import org.example.pmas.model.dto.SubProjectDTO;
import org.example.pmas.exception.DatabaseException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.SubProject;
import org.example.pmas.model.rowMapper.SubProjectDTORowMapper;
import org.example.pmas.model.rowMapper.SubProjectRowMapper;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubProjectRepository implements ISubProjectRepository {


    private final JdbcTemplate jdbcTemplate;


    public SubProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    public List<SubProject> getSubProjectsByProjectID(int projectId) {
//        String sql = "SELECT * FROM subprojects WHERE projectID = ?";
//        try {
//            return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Database error: couldn't get all task with associated project id: " + projectId, e);
//        }
//    }

//    public int getProjectIDBySubProjectID(int subprojectID) {
//        String sql = "SELECT projectID FROM subprojects WHERE id = ?";
//        try {
//            Integer result = jdbcTemplate.queryForObject(sql, new Object[]{subprojectID}, Integer.class);
//            return result != null ? result : 0;
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Database error: " + new NotFoundException(subprojectID));
//        }
//    }

    // Used for test
    // Implementet for further development. Ready to go.
    @Override
    public List<SubProject> readAll() {
        String sql = "SELECT * from subprojects";
        try {
            return jdbcTemplate.query(sql, new SubProjectRowMapper());
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public SubProject readSelected(int id) {
        String sql = "SELECT * FROM subprojects WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Database error: " + new NotFoundException(id));
        }
    }

    @Override
    public SubProject create(SubProject subProject) {
        String sql = "INSERT INTO subprojects (name, description, timeBudget, completed, projectID) VALUES (?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, subProject.getName());
                ps.setString(2, subProject.getDescription());
                ps.setObject(3, subProject.getTimeBudget(), java.sql.Types.DOUBLE);
                ps.setBoolean(4, subProject.isCompleted());
                ps.setInt(5, subProject.getProjectID());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }

        Number generatedKey = keyHolder.getKey();

        if (generatedKey != null) {
            subProject.setId(generatedKey.intValue());
            return subProject;
        }

        return null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM subprojects WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, id);
            //jdbc returns number of rows affected.
            return rows > 0;
        } catch (DataAccessException e) {
            throw new DatabaseException("Database error: " + new NotFoundException(id), e);
        }
    }

    @Override
    public boolean update(SubProject subproject) {
        String sql = "UPDATE subprojects SET name = ?, description = ?, timeBudget = ?, completed = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql,
                    subproject.getName(),
                    subproject.getDescription(),
                    subproject.getTimeBudget(),
                    subproject.isCompleted(),
                    subproject.getId()) > 0;
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean doesSubProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM subprojects WHERE id = ?)";
        try {
            //null safe way to check if result is true. Uses boolean object(true) to compare.
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<SubProjectDTO> getSubProjectDTOByProjectID(int projectID) {
        String sql = "SELECT " +
                "sp.id, " +
                "sp.name, " +
                "sp.description, " +
                "sp.completed, " +
                "sp.projectID, " +
                "sp.timeBudget, " +
                "COUNT(t.id) AS totalTasks, " +
                "SUM(IF(t.completed = true, 1, 0)) AS completedTasks, " +
                "SUM(IF(t.completed = true, t.timeBudget, 0)) AS timeTaken " +
                "FROM subprojects sp " +
                "LEFT JOIN tasks t ON sp.id = t.subProjectID " +
                "WHERE sp.projectID = ? " +
                "GROUP BY sp.id";
        try {
            return jdbcTemplate.query(sql, new SubProjectDTORowMapper(), projectID);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void updateSubProjectCompleted(int subProjectID, boolean completed) {
        String sql = "UPDATE subprojects SET completed = ? " +
                "WHERE id = ?";
        try {
            jdbcTemplate.update(sql, completed, subProjectID);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }
}
