package org.example.pmas.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SubProjectRepository implements CrudInterface{


    private final JdbcTemplate jdbcTemplate;


    public SubProjectRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }








    @Override
    public Object create(Object object) {
        return null;
    }

    @Override
    public List<Object> readAll() {
        return List.of();
    }

    @Override
    public Object readSelected() {
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
