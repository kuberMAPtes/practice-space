package com.kube.jpaprac.repository;

import com.kube.jpaprac.domain.Feed;

public interface FeedRepository {

    Feed save(Feed feed);

    void update(FeedDto feedDto);

    Feed findById(Long id);

    Feed findByTitleLike(String title);

    void deleteById(Long id);
}
