package org.example.pmas.service;

import org.example.pmas.exception.DeleteObjectException;
import org.example.pmas.exception.NotFoundException;
import org.example.pmas.model.Project;
import org.example.pmas.model.Role;
import org.example.pmas.model.Task;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IRoleRepository;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.example.pmas.repository.ProjectRepository;
import org.example.pmas.repository.TaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;


    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public User createUser(User newUser){
        try {
            //checks the return results of the method
            var user = userRepository.create(newUser);
            if (user == null) throw new NotFoundException(newUser.getUserID());
            //returns the method
            return userRepository.create(newUser);

            //error handling, if failing to reach DB
        }catch (DataAccessException dataAccessException){
            return null;
        }

    }

    public List<User> getAll(){
        try{
            return userRepository.readAll();
        }catch (DataAccessException dataAccessException) {
            return null;
        }
    }



    public User getUser(int userId) {
        try {
            User user = userRepository.readSelected(userId);
            List<Task> tasks = taskRepository.findAllByUserId(userId);
            List<Project> projects = projectRepository.readProjectsOfUser(userId);



//            for(Task task : tasks){
//                if(task.getSubProject() != null){
//                    int subprojectID = task.getSubProject().getId();
//                    int projectID = userRepository.getProjectIDOfUsersSubproject(userId, subprojectID);
//                    task.getSubProject().setProjectID(projectID);
//                }
//            }


            user.setTasks(tasks);
            user.setProjects(projects);

            return user;

        } catch (DataAccessException dataAccessException) {
            return null;
        }
    }


    public User logIn(String email, String password) {
        try {
            // checks if user exists
            var user = userRepository.getByEmail(email);

            // checks if password matches
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }


        } catch (DataAccessException dataAccessException) {
            return null;
        }
    }


    public void delete(int id) {
        var user = userRepository.readSelected(id);
        if(user == null) throw new NotFoundException(id);

        if(!userRepository.delete(id))
            throw new DeleteObjectException("Couldn't delete user with id: " + id);

    }

    public boolean updateUser(int id, User newUser){
        User oldUser = userRepository.readSelected(id);

        if(oldUser == null){
            throw new NotFoundException(id);
        }

        try {
          return userRepository.update(newUser);
        }catch (DataAccessException | IllegalArgumentException dataAccessException){
            dataAccessException.getMessage();
        }
        return false;
    }

    public User checkEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public List<Role> getAllRoles(){
        return roleRepository.readAll();

    }

}