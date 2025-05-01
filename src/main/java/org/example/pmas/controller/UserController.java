package org.example.pmas.controller;

import org.example.pmas.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

}
