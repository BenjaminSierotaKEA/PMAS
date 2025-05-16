package org.example.pmas.repository.Interfaces;

public interface CrudInterface<T> {

T create(T t);

T readSelected(int id);

boolean delete(int id);

boolean update(T newObject);

}




