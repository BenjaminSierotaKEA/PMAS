package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.rowMapper.SubProjectRowMapper;
import org.example.pmas.repository.Interfaces.ISubProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
        return jdbcTemplate.queryForObject(sql, new SubProjectRowMapper(), id);
    }

    public List<SubProject> getSubProjectsByProjectID(int projectId){
        String sql = "SELECT * FROM subproject WHERE projectsID = ?";
        return jdbcTemplate.query(sql, new SubProjectRowMapper(), projectId);
    }

    @Override
    public SubProject create(SubProject subProject) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM subprojects WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Object oldObject, Object newObject) {
        return false;
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
}
