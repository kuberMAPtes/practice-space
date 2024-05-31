package com.kube.jpaprac.repository;

import com.kube.jpaprac.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedJpaRepository extends JpaRepository<Feed, Long> {

    List<Feed> findFeedByTitleLike(String title);

    int countByTitleLike(String title);

    @Query("""
            SELECT f FROM Feed f WHERE f.title LIKE :title OR f.content = :content
            """)
    List<Feed> findFeedCustom(String title, String content);
}
