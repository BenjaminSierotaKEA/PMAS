package org.example.pmas.service;

import org.example.pmas.exception.UserNotFoundException;
import org.example.pmas.exception.WrongInputException;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserRepository userRepository;


    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser){
        try {
            //checks the return results of the method
            var user = userRepository.create(newUser);
            if (user == null) throw new UserNotFoundException(newUser.getUserID());
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
            return userRepository.readSelected(userId);
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
        if(user == null) throw new WrongInputException("Id not correct.");

        userRepository.delete(id);

    }

    public boolean updateUser(Object oldUser, Object newUser){
        try {
          return userRepository.update(oldUser,newUser);
        }catch (DataAccessException dataAccessException){
            dataAccessException.getMessage();
        }catch (IllegalArgumentException illegalArgumentException){
            illegalArgumentException.getMessage();
        }
        return false;
    }
}