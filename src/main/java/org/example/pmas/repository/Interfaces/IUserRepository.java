package org.example.pmas.repository.Interfaces;

import org.example.pmas.model.User;

import java.util.List;

public interface IUserRepository extends CrudInterface<User> {

    User getByEmail(String email);
    List<User> readAll();

    List<User> getAllOnProject(int projectID);
    List<User> getAllNotOnProject(int projectID);

    int getProjectIDOfUsersSubproject(int userID, int subprojectID);

    User readUserWithDetails(int userId);
}
