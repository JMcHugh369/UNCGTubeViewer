package com.example.backend.repository;

import com.example.backend.entity.LikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Integer> {
    Optional<LikeDislike> findByUserIdAndVideoId(int userId, int videoId);
}
