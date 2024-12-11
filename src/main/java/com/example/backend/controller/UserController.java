package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.entity.Video;
import com.example.backend.repository.VideoRepository;
import com.example.backend.service.UserService;
import com.example.backend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/createUser")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @GetMapping("/{id}/profile")
    public String showUserProfile(@PathVariable int id, Model model) {
        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(id));
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "profile";
        } else {
            return "error"; // or any other error page
        }
    }

    @PostMapping("/update-profile")
    public String updateUserProfile(@RequestParam String username,
                                    @RequestParam String firstName,
                                    @RequestParam String lastName,
                                    @RequestParam String email,
                                    Model model) {
        Optional<User> optionalUser = userService.getUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            userService.saveUser(user);
            model.addAttribute("user", user);
            return "profile";
        } else {
            return "error"; // or any other error page
        }
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createUser";
        }

        if (userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username is already taken");
            return "createUser";
        }

        if (userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email is already registered");
            return "createUser";
        }

        userService.saveUser(user);
        return "redirect:/users/home/" + user.getUsername();
    }

    public void likeVideo(int userId, int videoId) {
        User user = userService.getCurrentUser(userId);
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepository.save(video);
    }

}