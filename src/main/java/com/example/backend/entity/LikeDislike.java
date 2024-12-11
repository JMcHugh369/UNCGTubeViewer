package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "video_id"})})
public class LikeDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Enumerated(EnumType.STRING)
    private Action action; // Enum: LIKE or DISLIKE

    public enum Action {
        LIKE, DISLIKE
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "LikeDislike [id=" + id + ", user=" + user + ", video=" + video + ", action=" + action + "]";
    }
}