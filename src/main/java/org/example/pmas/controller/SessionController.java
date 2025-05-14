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

        User user = sessionHandler.getCurrentUser();


        if(loginSucceed && user.getRole().getName().equals("Employee")){
            return "redirect:" +"/"+ +user.getUserID()+"/user";
        }

        if(loginSucceed && user.getRole().getName().equals("Project Manager")){

            return "redirect:" +"/projects/all";
        }

        if(loginSucceed && user.getRole().getName().equals("Admin")){

            return "redirect:" +"/user-overview";
        }

        model.addAttribute("wrongCredentials", true);
        return "user-login";

    }


    @GetMapping("/logout")
    public String logUserOut() {
        sessionHandler.logOut();
        return "redirect:/";
    }


}
