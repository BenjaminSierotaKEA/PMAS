package org.example.pmas.repository;

import org.example.pmas.model.User;

import org.example.pmas.model.UserRowMapper;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

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

    @Override
    @Transactional
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users" +
                "WHERE email = ?";

        return jdbcTemplate.queryForObject(sql,
                new UserRowMapper(),
                email);
    }
}
