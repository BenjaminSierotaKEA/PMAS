package org.example.pmas.repository.Interfaces;

import java.util.List;

public interface CrudInterface<T> {

public T create(T t);

public List<T> readAll();

public T readSelected(int id);

public boolean delete(int id);

public boolean update(T newObject);

}




