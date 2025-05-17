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


// We need information about different tables when we use TaskRowMapper.
// You need to join users and subproject when making a query.
// You need to give
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setName(rs.getString("name"));
        if (hasColumn(rs, "priorityLevel"))
            task.setPriorityLevel(PriorityLevel.valueOf(rs.getString("priorityLevel")));
        task.setDescription(rs.getString("description"));
        task.setTimeBudget(rs.getDouble("timeBudget"));
        task.setCompleted(rs.getBoolean("completed"));
        if (hasColumn(rs, "deadline"))
            task.setDeadline(rs.getDate("deadline").toLocalDate());

        if (hasColumn(rs, "user_ids") && hasColumn(rs, "user_names"))
            task.setUsers(mapUsers(rs));

        task.setSubProject(new SubProject(rs.getInt("subProjectID")));

        return task;
    }

    // If needed, this method can check for a specific column.
    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName) != null && !rs.wasNull();
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
}
