package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile() {
        return "editProfile";
    }

    @GetMapping("/changePassword")
    public String changePassword() {
        return "changePassword";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount() {
        return "deleteAccount";
    }

    @PostMapping("/profile/{id}/upload")
    public String uploadProfilePicture(@PathVariable int id, @RequestParam("file") MultipartFile file, Model model) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setProfilePicture(file.getBytes());
                userRepository.save(user);
                return "redirect:/profile/" + id;
            } else {
                model.addAttribute("error", "User not found");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error uploading profile picture: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/profile/{id}/picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            byte[] image = user.getProfilePicture();
            if (image != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // or MediaType.IMAGE_PNG based on your image type
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.status(404).body(null); // No profile picture
            }
        } else {
            return ResponseEntity.status(404).body(null); // User not found
        }
    }

}