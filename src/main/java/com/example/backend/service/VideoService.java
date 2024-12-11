package com.example.backend.service;

import com.example.backend.entity.Comment;
import com.example.backend.entity.Video;
import com.example.backend.exceptionHandling.ResourceNotFoundException;
import com.example.backend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video getVideoById(int videoId) {
        return videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException("Invalid video ID"));
    }

    public int incrementLike(int videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepository.save(video);
        return video.getLikeCount();
    }

    public int incrementDislike(int videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found"));
        video.setDislikeCount(video.getDislikeCount() + 1);
        videoRepository.save(video);
        return video.getDislikeCount();
    }

    public void addComment(int videoId, String comment) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid video ID"));
        Comment newComment = new Comment();
        newComment.setText(comment);
        newComment.setVideo(video);
        video.getComment().add(newComment);
        videoRepository.save(video);
    }

    // VideoService.java
    public Video saveVideo(MultipartFile file, String title, String description) throws IOException {
        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoContent(file.getBytes());
        video.setCategory(file.getContentType());
        return videoRepository.save(video);
    }

    public void createVideo(Video video) {
        videoRepository.save(video);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}