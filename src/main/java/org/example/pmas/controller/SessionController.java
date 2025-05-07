package org.example.pmas.controller;

import org.example.pmas.model.User;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class SessionController {

    private final SessionHandler sessionHandler;



    public SessionController(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;

    }

    @GetMapping("/")
    public String getLogInPage(){
        return "user-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        boolean loginSucceed = sessionHandler.logIn(email,password);
        System.out.println("Login success? " + loginSucceed);


        if(loginSucceed){
            User user = sessionHandler.getCurrentUser();
            return "redirect:/session/"+user.getUserID()+"/user";
        }

        model.addAttribute("wrongCredentials", true);
        return "user-login";

    }

    @GetMapping("/session/{id}/user")
    public String userByID(@PathVariable("id") int id, Model model) {
        if (id <= 0) throw new IllegalArgumentException("Id can't be lower than 0");

        model.addAttribute("user", sessionHandler.getCurrentUser());

        return "user-page";
    }



}
