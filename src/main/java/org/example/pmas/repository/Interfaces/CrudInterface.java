package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.Task;

import java.util.List;

public interface CrudInterface<T> {

    T create(T t);

    T readSelected(int id);

    List<T> readAll();

    boolean delete(int id);

    boolean update(T newObject);

}




