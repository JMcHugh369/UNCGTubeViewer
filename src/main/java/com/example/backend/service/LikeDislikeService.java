package com.example.backend.service;

import com.example.backend.entity.LikeDislike;
import com.example.backend.entity.User;
import com.example.backend.entity.Video;
import com.example.backend.repository.LikeDislikeRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeDislikeService {

    @Autowired
    private LikeDislikeRepository likeDislikeRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    public void likeOrDislikeVideo(int userId, int videoId, LikeDislike.Action action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        // Check if the user has already liked or disliked this video
        Optional<LikeDislike> existingAction = likeDislikeRepository.findByUserIdAndVideoId(userId, videoId);

        if (existingAction.isPresent()) {
            LikeDislike likeDislike = existingAction.get();

            // If the action is the same, remove it (toggle off)
            if (likeDislike.getAction() == action) {
                likeDislikeRepository.delete(likeDislike);
                if (action == LikeDislike.Action.LIKE) {
                    video.setLikeCount(video.getLikeCount() - 1);
                } else {
                    video.setDislikeCount(video.getDislikeCount() - 1);
                }
            } else {
                // Update the action if it's different
                likeDislike.setAction(action);
                likeDislikeRepository.save(likeDislike);

                if (action == LikeDislike.Action.LIKE) {
                    video.setLikeCount(video.getLikeCount() + 1);
                    video.setDislikeCount(video.getDislikeCount() - 1);
                } else {
                    video.setDislikeCount(video.getDislikeCount() + 1);
                    video.setLikeCount(video.getLikeCount() - 1);
                }
            }
        } else {
            // New action
            LikeDislike likeDislike = new LikeDislike();
            likeDislike.setUser(user);
            likeDislike.setVideo(video);
            likeDislike.setAction(action);

            likeDislikeRepository.save(likeDislike);

            if (action == LikeDislike.Action.LIKE) {
                video.setLikeCount(video.getLikeCount() + 1);
            } else {
                video.setDislikeCount(video.getDislikeCount() + 1);
            }
        }

        videoRepository.save(video);
    }

    public List<Video> getLikedVideosByUserId(int userId) {
        return likeDislikeRepository.findAll()
                .stream()
                .filter(ld -> ld.getUser().getId() == userId && ld.getAction() == LikeDislike.Action.LIKE)
                .map(LikeDislike::getVideo)
                .collect(Collectors.toList());
    }
}
