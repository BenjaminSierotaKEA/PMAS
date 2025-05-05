package org.example.pmas.controller;

import jakarta.servlet.http.HttpSession;
import org.example.pmas.model.User;
import org.example.pmas.service.UserService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user-overview")
    public String allUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "admin-page";
    }

    @GetMapping("/{id}/user")
    public String userByID(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.getUser(id));
        return "user-page";
    }


    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        if (id <= 0) throw new

                IllegalArgumentException("Id not correct.");

        userService.delete(id);
        return "redirect:/tasks";
    }

}
