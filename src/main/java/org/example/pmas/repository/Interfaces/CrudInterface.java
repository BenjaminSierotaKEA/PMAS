package org.example.pmas.repository.Interfaces;

import java.util.List;

public interface CrudInterface<T> {

T create(T t);

List<T> readAll();

T readSelected(int id);

boolean delete(int id);

boolean update(T newObject);

}




