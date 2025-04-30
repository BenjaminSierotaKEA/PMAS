package org.example.pmas.service;

import org.example.pmas.repository.Interfaces.IUserRepository;

public class UserService {

    private final IUserRepository userRepository;



    public UserService(IUserRepository userRepository){
        this.userRepository=userRepository;
    }




}
