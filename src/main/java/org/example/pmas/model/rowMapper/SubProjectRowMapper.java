package org.example.pmas.model.rowMapper;

import org.example.pmas.model.SubProject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectRowMapper implements RowMapper<SubProject> {
    @Override
    public SubProject mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubProject sub = new SubProject();
        sub.setId(rs.getInt("id"));
        sub.setName(rs.getString("name"));
        sub.setDescription(rs.getString("description"));
        sub.setTimeBudget(rs.getInt("timeBudget"));
        sub.setCompleted(rs.getBoolean("completed"));
        sub.setProjectID(rs.getInt("projectID"));
        return sub;
    }
}
