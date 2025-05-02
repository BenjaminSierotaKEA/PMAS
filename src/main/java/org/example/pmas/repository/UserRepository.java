package org.example.pmas.repository;

import org.example.pmas.model.User;

import org.example.pmas.model.UserRowMapper;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.dao.DataAccessException;
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
    public User readSelected(int id) throws DataAccessException {
        String sql = "SELECT users.id, users.name, users.email, users.password" +
                "users.role, users.picture" +
                "FROM users" +
                "LEFT JOIN project ON users.id = project.id" +
                "where users.id = ?" +
                "GROUP BY users.id";

        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
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
    public User getByEmail(String email) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE LOWER(email) = LOWER(?)";

        return jdbcTemplate.queryForObject(sql,
                new UserRowMapper(),
                email);
    }
}
