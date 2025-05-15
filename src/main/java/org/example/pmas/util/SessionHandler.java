package org.example.pmas.util;

import jakarta.servlet.http.HttpSession;
import org.example.pmas.model.Role;
import org.example.pmas.model.User;

import org.example.pmas.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class SessionHandler {
    private final HttpSession session;
    private final UserService userService;
    private final int MAX_SESSION_LENGTH = 1800;

    public SessionHandler(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    //Checks if session is from a user
    public User getCurrentUser() {
        var user = session.getAttribute("user");
        if (user instanceof User) {
            return (User) user;
        }

        return null;
    }


    //log-in checker
    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    //gets the users role, used to determine READ/WRITE rights
    public Role getUserRole() {
        var user = getCurrentUser();

        return user.getRole();
    }

    //log user in of the credentials match in DB
    public boolean logIn(String email, String password) {
        var userExists = userService.logIn(email, password);

        if (userExists != null) {
            User user = new User(userExists.getUserID(), userExists.getName(), userExists.getRole());
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(MAX_SESSION_LENGTH);
            return true;
        }
        return false;
    }


    //removes the user from the session
    public void logOut() {
        session.removeAttribute("user");
    }


    //check the userID of the sessionUser against the ID from the DB
    public boolean isUserOwner(int ownerID) {
        var user = getCurrentUser();

        return user != null && user.getUserID() == ownerID;
    }

    public boolean isUserProjectManager() {
        if (isLoggedIn()) {
            return getUserRole().getName().equals("Project Manager");
        }
        return false;
    }

    public boolean isNotAdmin() {
        if (isLoggedIn()) {
            return !getUserRole().getName().equals("Admin");
        }
        return false;
    }


    //i dont know how to use the session handler so ill leave this commented out until
    //i figure it out
    //gets the users ID:
    /*
    public int getUserID(){
        if(getCurrentUser() != null){
           return getCurrentUser().getUserID();
        }
    }
    */


}
