package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.rowMapper.SubProjectRowMapper;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
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
        String sql = "SELECT * FROM subprojects";
        return jdbcTemplate.query(sql, new SubProjectRowMapper());
    }

    @Override
    public SubProject readSelected(int id) {
        String sql ="SELECT * FROM subprojects WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        String sql = "SELECT * FROM subprojects WHERE projectID = ?";
        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }

    @Override
    public SubProject create(SubProject subProject) {
        String sql = "INSERT INTO subprojects (name, description, timeBudget, timeTaken, completed, projectID) VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"}); // Specify "id" as the generated column
            ps.setString(1, subProject.getName());
            ps.setString(2, subProject.getDescription());
            ps.setDouble(3, subProject.getTimeBudget());
            ps.setDouble(4, subProject.getTimeTaken());
            ps.setBoolean(5, subProject.isCompleted());
            ps.setInt(6, subProject.getProjectID());
            return ps;
        }, keyHolder);

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
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(SubProject subproject) {
        String sql ="UPDATE subprojects SET name = ?, description = ?, timeBudget = ?, timeTaken = ?, completed = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                subproject.getName(),
                subproject.getDescription(),
                subproject.getTimeBudget(),
                subproject.getTimeTaken(),
                subproject.isCompleted(),
                subproject.getId()) > 0;
    }

    public int getProjectIDBySubProjectID(int subprojectID) {
        String sql = "SELECT projectID FROM subprojects WHERE id = ?";
        try {
            Integer result = jdbcTemplate.queryForObject(sql, new Object[]{subprojectID}, Integer.class);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public boolean doesSubProjectExist(int id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM subprojects WHERE id = ?)";
        //null safe way to check if result is true. Uses boolean object(true) to compare.
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, new Object[]{id}, Boolean.class));
    }


}
