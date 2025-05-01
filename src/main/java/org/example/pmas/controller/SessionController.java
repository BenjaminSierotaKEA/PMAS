package org.example.pmas.controller;


import jakarta.servlet.http.HttpSession;
import org.example.pmas.service.UserService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("session")
public class SessionController {

    private final SessionHandler sessionHandler;



    public SessionController(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;

    }

    @GetMapping("/user-login")
    public String getLogInPage(){
        return "user-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model redirectAttribute) {
        boolean loginSucceed = sessionHandler.logIn(email,password);

        if(loginSucceed){
            return "redirect:/";
        }
        redirectAttribute.addAttribute("WrongCredentials", true);
        return "user-login";


    }



}
