package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.User;

import java.util.List;

public interface IUserRepository extends CrudInterface<User> {

    public User getByEmail(String email);

    public List<User> getAllOnProject(int projectID);
    public List<User> getAllNotOnProject(int projectID);

}
