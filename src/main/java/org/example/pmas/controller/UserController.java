package org.example.pmas.controller;

import org.example.pmas.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

}
