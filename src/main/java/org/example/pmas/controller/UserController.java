package org.example.pmas.controller;

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
        model.addAttribute("newUser", new User());
        return "user-form";
    }

    @PostMapping("/user/create")
    public  String createUser(@ModelAttribute  User newUser, Model errorMessage){
        if (userService.checkEmail(newUser.getEmail()) != null) {
            errorMessage.addAttribute("emailTaken", true);
            return "user-form";
        }

        userService.createUser(newUser);

        return "redirect:/admin-page";

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
        return "admin-page";
    }

    @GetMapping("/{id}/update")
        public String updateUser(@PathVariable("id") int id, Model model){

        if(id <= 0) throw new
                IllegalArgumentException("ID not correct");

        model.addAttribute("user", userService.getUser(id));

        return "user-form";
    }

    @PostMapping("/{id}/update")
        public String updateUser(@PathVariable("id") int id, @ModelAttribute User newUser, Model model){

        boolean success = userService.updateUser(id,newUser);

        if(!success){
            model.addAttribute("errorMessage", "Failed to update, check your inserted values");
            return "user-form";
        }

        return "redirect:/user-overview";
    }

}
