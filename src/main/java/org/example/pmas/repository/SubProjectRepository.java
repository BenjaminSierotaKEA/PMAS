package org.example.pmas.repository;

import org.example.pmas.dto.SubProjectDTO;
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


    public SubProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public List<SubProject> readAll() {
        String sql = "SELECT * from subprojects";
        try {
            return jdbcTemplate.query(sql, new SubProjectRowMapper());
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke hente alle subprojekter", e);
        }
    }

    @Override
    public SubProject readSelected(int id) {
        String sql ="SELECT * FROM subprojects WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Database fejl: " + new NotFoundException(id) );
        }
    }

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        String sql = "SELECT * FROM subprojects WHERE projectID = ?";
        try {
            return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke hente alle subprojekter med projectId " + projectId );
        }
    }

    @Override
    public SubProject create(SubProject subProject) {
        String sql = "INSERT INTO subprojects (name, description, timeBudget, timeTaken, completed, projectID) VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                var ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, subProject.getName());
                ps.setString(2, subProject.getDescription());
                ps.setDouble(3, subProject.getTimeBudget());
                ps.setDouble(4, subProject.getTimeTaken());
                ps.setBoolean(5, subProject.isCompleted());
                ps.setInt(6, subProject.getProjectID());
                return ps;
            }, keyHolder);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke indsætte subprojekt",e);
        }

        Number generatedKey = keyHolder.getKey();

        if(generatedKey != null){
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
            throw new DatabaseException("Database fejl: " + new NotFoundException(id));
        }
    }

    @Override
    public boolean update(SubProject subproject) {
        String sql ="UPDATE subprojects SET name = ?, description = ?, timeBudget = ?, timeTaken = ?, completed = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql,
                    subproject.getName(),
                    subproject.getDescription(),
                    subproject.getTimeBudget(),
                    subproject.getTimeTaken(),
                    subproject.isCompleted(),
                    subproject.getId()) > 0;
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke opdatere subprojekt med Id " + subproject.getId(),e);
        }
    }

    public int getProjectIDBySubProjectID(int subprojectID) {
        String sql = "SELECT projectID FROM subprojects WHERE id = ?";
        try {
            Integer result = jdbcTemplate.queryForObject(sql, new Object[]{subprojectID}, Integer.class);
            return result != null ? result : 0;
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: " + new NotFoundException(subprojectID));
        }
    }

    public boolean doesSubProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM subprojects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        try {
            return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke kontrollere eksistensen af subprojekt med ID " + id,e);
        }
    }

    public List<SubProjectDTO> getSubProjectDTOByProjectID(int projectID) {
        String sql = "SELECT " +
                "sp.id, " +
                "sp.name, " +
                "sp.description, " +
                "sp.completed, " +
                "sp.projectID, " +
                "COUNT(t.id) AS totalTasks, " +
                "SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END) AS completedTasks, " +
                "SUM(CASE WHEN t.completed = true THEN t.timeTaken ELSE 0 END) AS timeTaken, " +
                "SUM(t.timeBudget) AS timeBudget " +
                "FROM subprojects sp " +                                                  // <-- and here
                "LEFT JOIN tasks t ON sp.id = t.subProjectID " +                          // <-- and here
                "WHERE sp.projectID = ? " +                                               // <-- and here
                "GROUP BY sp.id";
        try {
            return jdbcTemplate.query(sql, new SubProjectDTORowMapper(),projectID);
        } catch (DataAccessException e) {
            throw new DatabaseException("Database fejl: kunne ikke hente alle subprojekter", e);
        }
    }

}
