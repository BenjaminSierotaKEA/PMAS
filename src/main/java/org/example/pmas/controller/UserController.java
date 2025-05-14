package org.example.pmas.controller;

import org.example.pmas.model.Role;
import org.example.pmas.model.User;

import org.example.pmas.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/user-form")
    public String getUserForm(Model model) {
        List<Role> roles = userService.getAllRoles();
        User user = new User();
        user.setRole(new Role());

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "user-form";
    }

    @PostMapping("/user/create")
    public String createUser(@ModelAttribute User newUser, Model errorMessage) {
        if(newUser == null) throw new IllegalArgumentException("Something wrong with user.");


        if (userService.checkEmail(newUser.getEmail()) != null) {
            errorMessage.addAttribute("emailTaken", true);
            return "user-form";
        }

        userService.createUser(newUser);

        return "redirect:/user-overview";

    }


    @GetMapping("/user-overview")
    public String allUsers(Model model) {
        List<User> users = userService.getAll();

        model.addAttribute("users", users);

        return "admin-page";
    }

    @GetMapping("/{id}/user")
    public String userByID(@PathVariable("id") int id, Model model) {
        if(id <= 0) throw new IllegalArgumentException("Id not correct.");

        model.addAttribute("user", userService.getUser(id));
        return "user-page";
    }


    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        if (id <= 0) throw new IllegalArgumentException("Id not correct.");

        userService.delete(id);
        return "redirect:/user-overview";

    }

    @GetMapping("/{id}/update")
    public String updateUser(@PathVariable("id") int id, Model model) {
        if (id <= 0) throw new
                IllegalArgumentException("ID not correct");

        User user = userService.getUser(id);
        List<Role> roles = userService.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "user-form";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable("id") int id, @ModelAttribute User newUser, Model model) {
        if(newUser == null || id <= 0) throw new IllegalArgumentException("Something wrong with user.");

        boolean success = userService.updateUser(id, newUser);

        if (!success) {
            model.addAttribute("errorMessage", "Failed to update, check your inserted values");
            return "user-form";
        }

        if (userService.getUser(id).getRole() == null) {
            userService.getUser(id).setRole(new Role());
        }

        return "redirect:/user-overview";
    }

}
