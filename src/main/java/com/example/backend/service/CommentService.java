package com.example.backend.service;

import com.example.backend.entity.Comment;
import com.example.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByVideo(int videoId) {
        // Implement logic to fetch comments by video ID
        return null;
    }
}