package com.itsqmet.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new com.itsqmet.entity.User());
        return "pages/registerUser";
    }

    // Indicate where users go after authentication
    @GetMapping("/postLogin")
    public String redirectByRole(Authentication authentication) {
        // Get user that is authenticated
        User user = (User) authentication.getPrincipal();
        // Get list of roles that has the user
        String role = user.getAuthorities().stream()
                // Extract the role name
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElse("");

        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/users";
            case "ROLE_MODERATOR" -> "redirect:/movies/admin";
            case "ROLE_USER" -> "redirect:/movies";
            default -> "redirect:/login";
        };
    }
}
