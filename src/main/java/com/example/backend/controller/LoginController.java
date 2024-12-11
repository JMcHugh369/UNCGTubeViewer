package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showLoginPage() {
        return "login"; // Renders login.html
    }

    @PostMapping()
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("userPassword") String password,
            Model model) {
        Optional<User> userOpt = userService.getUserByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getUserPassword().equals(password)) {
            model.addAttribute("username", username);
            return "redirect:/users/home/" + username; // Ensure the URL is correctly formatted
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Stay on login page if authentication fails
        }
    }
}
