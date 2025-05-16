package org.example.pmas.model.rowMapper;

import org.example.pmas.model.dto.ProjectDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDTORowMapper implements RowMapper<ProjectDTO> {
    @Override
    public ProjectDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setDescription(rs.getString("description"));
        dto.setTimeBudget(rs.getDouble("timeBudget"));
        dto.setTimeTaken(rs.getDouble("timeTaken"));
        dto.setCompleted(rs.getBoolean("completed"));
        dto.setDeadline(rs.getDate("deadline").toLocalDate());
        dto.setTotalSubProjects(rs.getInt("totalSubProjects"));
        dto.setCompletedSubProjects(rs.getInt("completedSubProjects"));
        return dto;
    }
}
