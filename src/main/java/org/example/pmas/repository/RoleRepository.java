package org.example.pmas.repository;

import org.example.pmas.exception.DatabaseException;
import org.example.pmas.model.Role;
import org.example.pmas.model.rowMapper.RoleRowMapper;
import org.example.pmas.repository.Interfaces.CrudInterface;
import org.example.pmas.repository.Interfaces.IRoleRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RoleRepository implements IRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public List<Role> readAll() {
        String sql = "SELECT * FROM roles";

        try {
            return jdbcTemplate.query(sql, new RoleRowMapper());
        } catch (DataAccessException e)
        {
            throw new DatabaseException(e);
        }

    }
}
