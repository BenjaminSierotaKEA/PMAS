package org.example.pmas.util;

import jakarta.servlet.http.HttpSession;
import org.example.pmas.model.User;
import org.example.pmas.repository.Role;
import org.example.pmas.repository.UserRepository;
import org.example.pmas.service.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessionHandler {

    private UserService userService;

    public SessionHandler(UserService userService){
        this.userService=userService;
    }

    //Checks if session is from a user
    public User getCurrentUser(HttpSession session){
        var user = session.getAttribute("user");
        if (user instanceof User){
            return (User) user;
        }

        return null;
    }


    //log-in checker
    public boolean isLoggedIn(HttpSession session) {
        return getCurrentUser(session) != null;
    }

    //gets the users role, used to determine READ/WRITE rights
    public Role getUserRole(HttpSession session){
        var user = getCurrentUser(session);

        return user.getRole();
    }

    //log user in of the credentials match in DB
    public boolean logIn(String email, String password){
        var userExists = userService.logIn(email,password);
        if (userExists != null){

        return true;
        }
        return false;
    }


    //removes the user from the session
    public void logOut(HttpSession session){
        session.removeAttribute("user");
    }




    //check the userID of the sessionUser against the ID from the DB
    public boolean isUserOwner(HttpSession session, int ownerID){
        var user = getCurrentUser(session);

        return user != null && user.getUserID() == ownerID;
    }



}
