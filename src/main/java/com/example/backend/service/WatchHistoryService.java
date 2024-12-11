package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.entity.Video;
import com.example.backend.entity.WatchHistory;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VideoRepository;
import com.example.backend.repository.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WatchHistoryService {

    @Autowired
    private WatchHistoryRepository watchHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public void saveWatchHistory(int userId, int videoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));
        WatchHistory watchHistory = new WatchHistory();
        watchHistory.setUser(user);
        watchHistory.setVideo(video);
        watchHistory.setWatchedAt(LocalDateTime.now());

        watchHistoryRepository.save(watchHistory);
    }

    public List<WatchHistory> getWatchHistoryByUserId(int userId) {
        return watchHistoryRepository.findByUserId(userId);
    }
}