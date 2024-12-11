package com.example.backend.service;

import com.example.backend.entity.Comment;
import com.example.backend.entity.User;
import com.example.backend.entity.Video;
import com.example.backend.exceptionHandling.ResourceNotFoundException;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CommentRepository commentRepository;

    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getCurrentUser(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Optional<User> getCurrentUserByOp(int userId) {
        return userRepository.findById(userId);
    }

    public void updateUserProfilePicturePath(int userId, String filePath) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfilePicture(filePath.getBytes());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void likeVideo(String username, int videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepository.save(video);
    }

    public void dislikeVideo(String username, int videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setDislikeCount(video.getDislikeCount() + 1);
        videoRepository.save(video);
    }

    public void addComment(String username, int videoId, String commentText) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        Comment comment = new Comment();
        comment.setVideo(video);
        comment.setText(commentText);
        commentRepository.save(comment);
    }
}