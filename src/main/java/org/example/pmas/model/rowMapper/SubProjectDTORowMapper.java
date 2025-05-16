package org.example.pmas.model.rowMapper;

import org.example.pmas.model.dto.SubProjectDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubProjectDTORowMapper implements RowMapper<SubProjectDTO> {
    @Override
    public SubProjectDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SubProjectDTO dto = new SubProjectDTO();
        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setDescription(rs.getString("description"));
        dto.setTimeBudget(rs.getInt("timeBudget"));
        dto.setTimeTaken(rs.getDouble("timeTaken"));
        dto.setCompleted(rs.getBoolean("completed"));
        dto.setProjectID(rs.getInt("projectID"));
        dto.setTotalTasks(rs.getInt("totalTasks"));
        dto.setCompletedTasks(rs.getInt("completedTasks"));
        return dto;
    }
}