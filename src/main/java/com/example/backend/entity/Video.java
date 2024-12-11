package com.example.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "likedVideos")
    private List<User> likedByUsers;

    private int likeCount = 0;
    private int dislikeCount = 0;

    private String title;
    private String description;
    private String category;

    // Store video as a BLOB (binary data)
    @Lob
    private byte[] videoContent;  // Store video data here as a BLOB in the DB

    private String thumbnailUrl;
    private boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchHistory> watchHistory = new ArrayList<>();

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeDislike> likeDislikeActions = new ArrayList<>();



    // Getters and Setters
    public List<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<LikeDislike> getLikeDislikeActions() {
        return likeDislikeActions;
    }

    public void setLikeDislikeActions(List<LikeDislike> likeDislikeActions) {
        this.likeDislikeActions = likeDislikeActions;
    }

    public List<WatchHistory> getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(List<WatchHistory> watchHistory) {
        this.watchHistory = watchHistory;
    }

    public List<Comment> getComment() {
        return comments;
    }

    public void setComment(List<Comment> comment) {
        this.comments = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(byte[] videoContent) {
        this.videoContent = videoContent;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", isPublic=" + isPublic +
                ", user=" + user +
                ", comment=" + comments +
                '}';
    }

    public void addComment(String comment) {
        Comment newComment = new Comment();
        newComment.setText(comment);
        newComment.setVideo(this);
        comments.add(newComment);
    }
}