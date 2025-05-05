package org.example.pmas.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        var user = new User();
        final int USER_ID = rs.getInt("id");
        user.setUserID(USER_ID);
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPicture(rs.getString("picture"));

        int roleId = rs.getInt("role");
        user.setRole(roleSetter(roleId));


        return user;

    }

    private Role roleSetter(int roleNumber) {
        return switch (roleNumber) {
            case 1 -> new Role(1, "Admin");
            case 2 -> new Role(2, "Project Manager");
            case 3 -> new Role(3, "Employee");
            default -> throw new IllegalArgumentException("Unknown role number: " + roleNumber);
        };
    }



}
