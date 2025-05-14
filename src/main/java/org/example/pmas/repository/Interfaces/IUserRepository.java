package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface IUserRepository extends CrudInterface<User> {

    public User getByEmail(String email);

    @Transactional
    int getProjectIDOfUsersSubproject(int userID, int subprojectID);
}
