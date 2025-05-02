package org.example.pmas.model.rowMapper;

import org.example.pmas.model.Task;
import org.example.pmas.model.User;
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
        task.setDescription(rs.getString("description"));
        task.setCompleted(rs.getBoolean("completed"));
        task.setUsers(mapUsers(rs));

        return task;
    }

    // Return users or null
    private Set<User> mapUsers(ResultSet rs) throws SQLException {
        Set<User> users = new HashSet<>();

        String username = rs.getString("user_names");
        String[] usernames = username.split(",");

        String id = rs.getString("user_ids");
        String[] ids = id.split(",");

        for (int i = 0; i < ids.length; i++) {
            User user = new User();
            user.setId(Integer.parseInt(ids[i]));
            user.setName(usernames[i]);
            users.add(user);
        }

        return users;
    }
}
