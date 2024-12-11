package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.entity.Video;
import com.example.backend.repository.VideoRepository;
import com.example.backend.service.UserService;
import com.example.backend.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/videos")
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/videos/{id}")
    public String showVideo(@PathVariable int id, Model model, Principal principal) {
        Optional<Video> videoOptional = Optional.ofNullable(videoService.getVideoById(id));
        if (videoOptional.isPresent()) {
            Video video = videoOptional.get();
            String base64Video = Base64.getEncoder().encodeToString(video.getVideoContent());
            model.addAttribute("video", video);
            model.addAttribute("base64Video", base64Video);

            // Add user to the model
            if (principal != null) {
                String username = principal.getName();
                User user = (User) userService.getByUsername(username);
                model.addAttribute("user", user);
            }

            return "video";
        } else {
            return "error"; // or any other error page
        }
    }

    @GetMapping("/create")
    public String showCreateVideoForm(Model model) {
        model.addAttribute("video", new Video());
        return "createVideo";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createVideo(@RequestParam("file") MultipartFile file,
                                              @RequestParam("title") String title,
                                              @RequestParam("description") String description) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Video file cannot be empty");
            }

            Video video = new Video();
            video.setTitle(title);
            video.setDescription(description);
            video.setVideoContent(file.getBytes());
            video.setCategory(file.getContentType());

            videoService.createVideo(video);
            return ResponseEntity.ok("Video created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid video content: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating video: " + e.getMessage());
        }
    }


    @PostMapping("/video/{id}/like")
    public ResponseEntity<Void> likeVideo(@PathVariable int id, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            userService.likeVideo(username, id);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/video/{id}/dislike")
    public ResponseEntity<Void> dislikeVideo(@PathVariable int id, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            userService.dislikeVideo(username, id);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/video/{id}/comments")
    public ResponseEntity<String> addComment(@PathVariable int id, @RequestParam String comment, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            userService.addComment(username, id, comment);
        }
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<byte[]> getVideo(@PathVariable("id") int id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", video.getCategory());
        return new ResponseEntity<>(video.getVideoContent(), headers, HttpStatus.OK);
    }

}