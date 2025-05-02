package org.example.pmas.service;

import org.example.pmas.exception.UserNotFoundException;
import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepository;


    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(int userId) {
        // controls if user exists.
        try {
            var user = userRepository.readSelected(userId);
            if (user == null) throw new UserNotFoundException(userId);

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


}