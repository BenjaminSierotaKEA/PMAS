package org.example.pmas.repository.Interfaces;

import java.util.List;

public interface CrudInterface {

public Object create(Object object);

public List<Object> readAll();

public Object readSelected();

public boolean delete(int id);

public boolean update(Object oldObject, Object newObject);

}




