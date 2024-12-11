package com.example.backend.repository;

import com.example.backend.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Integer> {
    Optional<WatchHistory> findById(Integer videoId);

    List<WatchHistory> findByUserId(int userId);
}