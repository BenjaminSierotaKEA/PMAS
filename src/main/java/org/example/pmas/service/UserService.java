package org.example.pmas.service;

import org.example.pmas.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;



    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }




}
