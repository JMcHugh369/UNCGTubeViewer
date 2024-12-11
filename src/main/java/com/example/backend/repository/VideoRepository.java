package com.example.backend.repository;

import com.example.backend.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Optional<Video> findById(int videoId);
}