package org.example.pmas.repository;

import org.example.pmas.model.User;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserRepository implements org.example.pmas.repository.Interfaces.IUserRepository {

    private final JdbcTemplate jdbcTemplate;


    public UserRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return List.of();
    }

    @Override
    public User readSelected() {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Object oldObject, Object newObject) {
        return false;
    }
}
