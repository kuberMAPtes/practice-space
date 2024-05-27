package com.kube.jpaprac.repository;

import com.kube.jpaprac.domain.Feed;

public class FeedJpaRepository implements FeedRepository {

    @Override
    public Feed save(Feed feed) {
        return null;
    }

    @Override
    public void update(FeedDto feedDto) {

    }

    @Override
    public Feed findById(Long id) {
        return null;
    }

    @Override
    public Feed findByTitleLike(String title) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
