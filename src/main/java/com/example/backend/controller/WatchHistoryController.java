package com.example.backend.controller;

import com.example.backend.entity.WatchHistory;
import com.example.backend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watch-history")
public class WatchHistoryController {

    @Autowired
    private WatchHistoryService watchHistoryService;

    @PostMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<String> addWatchHistory(@PathVariable int userId, @PathVariable int videoId) {
        watchHistoryService.saveWatchHistory(userId, videoId);
        return ResponseEntity.ok("Watch history saved successfully.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<WatchHistory>> getWatchHistory(@PathVariable int userId) {
        return ResponseEntity.ok(watchHistoryService.getWatchHistoryByUserId(userId));
    }
}

