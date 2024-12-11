package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showHomePage(@RequestParam("id") int userId, Model model) {
        Optional<User> userOptional = userService.getCurrentUserByOp(userId);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            // Handle the case where the user is not present
            model.addAttribute("user", new User()); // or redirect to a login page
        }
        return "viewer_home";
    }

    @GetMapping("/users/home/{username}")
    public String home(@PathVariable("username") String username, Model model) {
        User user = userService.getCurrentUser(username);
        model.addAttribute("user", user);
        return "viewer_home";
    }

    @GetMapping("/createUser")
    public String createUser() {
        return "createUser";
    }

    @GetMapping("/trending")
    public String trending() {
        return "trending";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/subscriptions")
    public String subscriptions() {
        return "subscriptions";
    }

    @GetMapping("/likedVids")
    public String likedVids() {
        return "likedVids";
    }

    @GetMapping("/createCommentHome")
    public String createCommentHome() {
        return "createComment";
    }
}