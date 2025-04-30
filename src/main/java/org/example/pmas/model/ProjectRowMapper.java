package org.example.pmas.model;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;



public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
