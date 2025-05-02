package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.User;

public interface IUserRepository extends CrudInterface<User> {

    public User getByEmail(String email);
}
