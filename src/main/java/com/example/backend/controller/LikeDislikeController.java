package com.example.backend.controller;

import com.example.backend.entity.LikeDislike;
import com.example.backend.entity.Video;
import com.example.backend.service.LikeDislikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like-dislike")
public class LikeDislikeController {

    @Autowired
    private LikeDislikeService likeDislikeService;

    @PostMapping("/{userId}/videos/{videoId}/{action}")
    public ResponseEntity<String> likeOrDislikeVideo(
            @PathVariable int userId,
            @PathVariable int videoId,
            @PathVariable String action) {

        LikeDislike.Action likeDislikeAction;
        try {
            likeDislikeAction = LikeDislike.Action.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid action. Use LIKE or DISLIKE.");
        }

        likeDislikeService.likeOrDislikeVideo(userId, videoId, likeDislikeAction);
        return ResponseEntity.ok("Action processed successfully.");
    }

    @GetMapping("/{userId}/liked-videos")
    public ResponseEntity<List<Video>> getLikedVideos(@PathVariable int userId) {
        return ResponseEntity.ok(likeDislikeService.getLikedVideosByUserId(userId));
    }
}
