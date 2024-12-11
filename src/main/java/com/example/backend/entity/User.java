package com.example.backend.entity;

import com.example.backend.enums.UserType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"username"})
})
public class User {

    public static String getUsername;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String userPassword;
    private String userStatus;
    private LocalDate createdAt;

    @Lob
    private byte[] profilePicture;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WatchHistory> watchHistory = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeDislike> likeDislikeActions = new ArrayList<>();

    public User() {
        this.comments = new ArrayList<>();
        this.watchHistory = new ArrayList<>();
        this.likeDislikeActions = new ArrayList<>();
    }

    public User(String username, String email, String firstName, String lastName, String userPassword, String userStatus, LocalDate createdAt, UserType userType) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.userType = userType;
        this.comments = new ArrayList<>();
        this.watchHistory = new ArrayList<>();
        this.likeDislikeActions = new ArrayList<>();
    }

    @ManyToMany
    @JoinTable(
            name = "user_liked_videos",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private List<Video> likedVideos;

    public static String getGetUsername() {
        return getUsername;
    }

    public static void setGetUsername(String getUsername) {
        User.getUsername = getUsername;
    }

    public List<Video> getLikedVideos() {
        return likedVideos;
    }

    public void setLikedVideos(List<Video> likedVideos) {
        this.likedVideos = likedVideos;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public List<WatchHistory> getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(List<WatchHistory> watchHistory) {
        this.watchHistory = watchHistory;
    }

    public void addWatchHistory(WatchHistory watchHistory) {
        this.watchHistory.add(watchHistory);
    }

    public void removeWatchHistory(WatchHistory watchHistory) {
        this.watchHistory.remove(watchHistory);
    }

    public List<LikeDislike> getLikeDislikeActions() {
        return likeDislikeActions;
    }

    public void setLikeDislikeActions(List<LikeDislike> likeDislikeActions) {
        this.likeDislikeActions = likeDislikeActions;
    }

    public void addLikeDislikeAction(LikeDislike likeDislike) {
        this.likeDislikeActions.add(likeDislike);
    }

    public void removeLikeDislikeAction(LikeDislike likeDislike) {
        this.likeDislikeActions.remove(likeDislike);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}