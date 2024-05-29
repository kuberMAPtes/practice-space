package com.kube.jpaprac.hibernate;

import com.kube.jpaprac.domain.Feed;
import com.kube.jpaprac.domain.Member;
import com.kube.jpaprac.repository.FeedJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class TestFeedJpaRepository {

    @Autowired
    FeedJpaRepository jpaRepository;

    @Test
    void save() {
        Member writer = new Member();
        writer.setMemberId("scott");
        writer.setMemberName("tiger");

        Feed feed = new Feed("common", "content", writer);
        this.jpaRepository.save(feed);
    }

    @Test
    void findById() {
        Member writer = new Member();
        writer.setMemberId("scott");
        writer.setMemberName("tiger");

        Feed feed = new Feed("common", "content", writer);

        log.info("transientFeed={}", feed);

        this.jpaRepository.save(feed);

        log.info("persistFeed={}", feed);

        Optional<Feed> byId = this.jpaRepository.findById(feed.getFeedId());
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get()).isEqualTo(feed);
    }

    @Test
    void update() {
        Member writer = new Member();
        writer.setMemberId("scott");
        writer.setMemberName("tiger");

        Feed feed = new Feed("common", "content", writer);

        this.jpaRepository.save(feed);

        Feed findFeed = this.jpaRepository.findById(feed.getFeedId()).get();

        findFeed.setTitle("new title");
        findFeed.setContent("asiodioasv");

        Feed findFeed2 = this.jpaRepository.findById(feed.getFeedId()).get();

        assertThat(findFeed2.getTitle()).isEqualTo(findFeed.getTitle());
        assertThat(findFeed2.getContent()).isEqualTo(findFeed.getContent());
    }

    @Test
    void findLike() {
        Member member = new Member();
        member.setMemberId("id1");
        member.setMemberName("memberName");
        this.jpaRepository.saveAll(List.of(
                new Feed("title1", "content", member),
                new Feed("title2", "content", member),
                new Feed("title3", "content", member),
                new Feed("title4", "content", member),
                new Feed("out of rule", "content", member),
                new Feed("out of rule2", "content", member)
        ));

        List<Feed> feeds = this.jpaRepository.findFeedByTitleLike("%title%");
        feeds.forEach((feed) -> {
            log.info("feed.title={}", feed.getTitle());
            log.info("feed=content={}", feed.getContent());
            log.info("feed.writer.memberName={}", feed.getWriter().getMemberId());
        });
    }

    @Test
    void count() {
        Member member = new Member();
        member.setMemberId("id1");
        member.setMemberName("memberName");
        this.jpaRepository.saveAll(List.of(
                new Feed("title1", "content", member),
                new Feed("title2", "content", member),
                new Feed("title3", "content", member),
                new Feed("title4", "content", member),
                new Feed("out of rule", "content", member),
                new Feed("out of rule2", "content", member)
        ));

        assertThat(this.jpaRepository.count()).isEqualTo(6);
    }

    @Test
    void countByTitleLike() {
        Member member = new Member();
        member.setMemberId("id1");
        member.setMemberName("memberName");
        this.jpaRepository.saveAll(List.of(
                new Feed("title1", "content", member),
                new Feed("title2", "content", member),
                new Feed("title3", "content", member),
                new Feed("title4", "content", member),
                new Feed("out of rule", "content", member),
                new Feed("out of rule2", "content", member)
        ));

        assertThat(this.jpaRepository.countByTitleLike("title%")).isEqualTo(4);
    }

    @Test
    void findByCustom() {
        Member member = new Member();
        member.setMemberId("id1");
        member.setMemberName("memberName");
        this.jpaRepository.saveAll(List.of(
                new Feed("title1", "a", member),
                new Feed("title2", "b", member),
                new Feed("title3", "c", member),
                new Feed("title4", "d", member),
                new Feed("out of rule", "e", member),
                new Feed("out of rule2", "f", member)
        ));

        List<Feed> feeds = this.jpaRepository.findFeedCustom("title%", "e");
        assertThat(feeds.stream().map(Feed::getTitle)).contains(
                "title1",
                "title2",
                "title3",
                "title4",
                "out of rule"
        );
    }
}
