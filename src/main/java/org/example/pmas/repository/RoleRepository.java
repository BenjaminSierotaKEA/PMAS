package org.example.pmas.repository;

import org.example.pmas.model.Role;
import org.example.pmas.model.rowMapper.RoleRowMapper;
import org.example.pmas.repository.Interfaces.IRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepository implements IRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role create(Role role) {
        return null;
    }

    @Override
    public List<Role> readAll(){
        String sql = "SELECT * FROM roles";

        return jdbcTemplate.query(sql,new RoleRowMapper());

    }

    @Override
    public Role readSelected(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Role newObject) {
        return false;
    }


}
