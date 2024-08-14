package com.lcaohoanq.repository;

import com.lcaohoanq.entity.Score;
import com.lcaohoanq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Score findByUser(User user);

}
