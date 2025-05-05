package org.example.pmas.model.rowMapper;


import org.example.pmas.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(rs.getInt("id"));
        project.setName(rs.getString("name"));
        project.setDescription(rs.getString("description"));
        project.setTimeBudget(rs.getInt("timebudget"));
        project.setDeadline(rs.getObject("deadline", LocalDateTime.class));

        return project;
    }
}
