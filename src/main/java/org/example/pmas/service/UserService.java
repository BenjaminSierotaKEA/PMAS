package org.example.pmas.service;

import org.example.pmas.model.User;
import org.example.pmas.repository.Interfaces.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final IUserRepository userRepository;



    public UserService(IUserRepository userRepository){
        this.userRepository=userRepository;
    }



    public User logIn(String email, String password) {
        // checks if user exists
        var user = userRepository.getByEmail(email);
        if (user == null) return null;

        // checks if password matches
        return user.getPassword().equals(password)
                //if true
                ? user
                //else
                : null;
    }



}
