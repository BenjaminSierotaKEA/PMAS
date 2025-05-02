package org.example.pmas.model.rowMapper;

import org.example.pmas.model.SubProject;
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

        // If subproject is in query
        if(rs.findColumn("subproject_name") > 0)
            task.setSubProject(mapSubproject(rs));

        return task;
    }

    // Return users or null
    private Set<User> mapUsers(ResultSet rs) throws SQLException {
        Set<User> users = new HashSet<>();

        // Names are concatenated with ','
        String username = rs.getString("user_names");
        String[] usernames = username.split(",");

        // Users are concatenated with ','
        String id = rs.getString("user_ids");
        String[] ids = id.split(",");

        // adds a user to users
        for (int i = 0; i < ids.length; i++) {
            User user = new User();
            user.setId(Integer.parseInt(ids[i]));
            user.setName(usernames[i]);
            users.add(user);
        }

        return users;
    }

    private SubProject mapSubproject(ResultSet rs) throws SQLException {
        SubProject subproject = new SubProject();
        subproject.setName(rs.getString("subproject_name"));

        return subproject;
    }
}
