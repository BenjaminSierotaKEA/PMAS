package org.example.pmas.repository;

import org.example.pmas.model.SubProject;
import org.example.pmas.repository.Interfaces.SubProjectRepositoryInterface;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SubProjectRepository implements SubProjectRepositoryInterface {


    private final JdbcTemplate jdbcTemplate;


    public SubProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }


    @Override
    public SubProject create(SubProject subProject) {
        return null;
    }

    @Override
    public List<SubProject> readAll() {
        return List.of();
    }

    @Override
    public SubProject readSelected() {
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
