package org.example.pmas.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {
    @Override
    public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
