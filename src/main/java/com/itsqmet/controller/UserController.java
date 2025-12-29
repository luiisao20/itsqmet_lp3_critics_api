package com.itsqmet.controller;

import com.itsqmet.entity.User;
import com.itsqmet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "pages/admin";
    }

    @PostMapping("/registerUser")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "pages/registerUser";
        }
        if (user.getUuid() != null) {
            userService.updateUser(user.getUuid(), user);
            return "redirect:/users";
        }
        userService.registerUser(user);
        return "redirect:/movies";
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
    }
}
