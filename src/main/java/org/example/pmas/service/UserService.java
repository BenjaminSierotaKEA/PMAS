package org.example.pmas.service;

import org.example.pmas.exception.DatabaseException;
import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.Role;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IProjectRepository;
import org.example.pmas.repository.Interfaces.IRoleRepository;
import org.example.pmas.repository.Interfaces.ITaskRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.example.pmas.util.SortList;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;



    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, ITaskRepository taskRepository, IProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;

    }

    public void createUser(User newUser){
        try {
            //checks the return results of the method
            var user = userRepository.create(newUser);
            if (user == null) throw new NotFoundException(newUser.getUserID());
            //error handling, if failing to reach DB
        }catch (DataAccessException e){
            throw new DatabaseException(e);
        }

    }

    public List<User> getAll(){
        try{
            return userRepository.readAll();
        }catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }



    public User getUser(int userId) {
        try {
            User user = userRepository.readSelected(userId);

            if (user == null) {
                throw new NotFoundException("Database not containing User, not found for id " + userId);
            }

            //Filling out variables for user:
             List<Task> sortedTasks = SortList.tasksDeadlinePriority(taskRepository.findAllByUserId(userId));
             user.setTasks(sortedTasks);
             user.setProjects(projectRepository.readProjectsOfUser(userId));

            return user;

        } catch (DataAccessException e) {
            throw new DatabaseException("Could not access database, or sql failed.",e);
        }
    }


    public User logIn(String email, String password) {
        try {
            // checks if user exists
            var user = userRepository.getByEmail(email);

            // checks if password matches
            if (user != null && user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }


        } catch (NotFoundException e) {
            throw new NotFoundException("Database error: could not find user in database.");
        }
    }


    public void delete(int id) {
        var user = userRepository.readSelected(id);
        if(user == null) throw new NotFoundException(id);

        if(!userRepository.delete(id))
            throw new DeleteObjectException(id);

    }

    public boolean updateUser(int id, User newUser){
        User oldUser = userRepository.readSelected(id);

        if(oldUser == null){
            throw new NotFoundException(id);
        }

        try {
          return userRepository.update(newUser);
        }catch (DataAccessException e){
            throw new DatabaseException(e);
        }
    }

    public User checkEmail(String email) {
        try {
            return userRepository.getByEmail(email);
        }catch (DataAccessException e){
            throw new DatabaseException(e);
        }
    }

    public List<Role> getAllRoles(){
        try {
            return roleRepository.readAll();
        } catch ( DataAccessException e) {
            throw new DatabaseException(e);
        }

    }

}