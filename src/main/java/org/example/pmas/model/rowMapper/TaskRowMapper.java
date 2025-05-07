package org.example.pmas.model.rowMapper;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.model.enums.PriorityLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setName(rs.getString("name"));
        if (rs.getString("priorityLevel") != null && !rs.wasNull())
            task.setPriorityLevel(PriorityLevel.valueOf(rs.getString("priorityLevel")));
        task.setDescription(rs.getString("description"));
        task.setTimeBudget(rs.getDouble("timeBudget"));
        task.setTimeTaken(rs.getDouble("timeTaken"));
        task.setCompleted(rs.getBoolean("completed"));
        if (rs.getDate("deadline") != null && !rs.wasNull())
            task.setDeadline(rs.getDate("deadline").toLocalDate());

        if (rs.getString("user_names") != null && rs.getString("user_ids") != null)
            task.setUsers(mapUsers(rs));

        // If subproject is in query
        if (hasColumn(rs, "subproject_name"))
            task.setSubProject(mapSubproject(rs));

        return task;
    }

    // If needed, this method can check for a specific column.
    private boolean hasColumn(ResultSet rs, String columnName) {
        // findColumn throws if not exist. That's why try catch
        try {
            return rs.findColumn(columnName) > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // Return users or null
    private Set<User> mapUsers(ResultSet rs) throws SQLException {
        Set<User> users = new HashSet<>();
        String GROUP_CONCAT_SEPARATOR = ",";

        // Names are concatenated with ','
        String username = rs.getString("user_names");

        String[] usernames = username.split(GROUP_CONCAT_SEPARATOR);

        // Users are concatenated with ','
        String id = rs.getString("user_ids");
        String[] ids = id.split(GROUP_CONCAT_SEPARATOR);

        // adds a user to users
        for (int i = 0; i < ids.length; i++) {
            User user = new User(
                    Integer.parseInt(ids[i]),
                    usernames[i]);
            users.add(user);
        }

        return users;
    }

    // maps SubProject only by name.
    private SubProject mapSubproject(ResultSet rs) throws SQLException {
        SubProject subproject = new SubProject();
        subproject.setId(rs.getInt("subproject_id"));
        subproject.setName(rs.getString("subproject_name"));

        return subproject;
    }
}
