package com.kube.jpaprac.advancedmapping;

import com.kube.jpaprac.config.HibernateConfig;
import com.kube.jpaprac.domain.Feed;
import com.kube.jpaprac.domain.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(classes = HibernateConfig.class)
public class TestManyToMany {

    @Autowired
    SessionFactory sessionFactory;

    Session session;

    @BeforeEach
    void init() {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
    }

    @AfterEach
    void afterEach() {
        if (this.session != null) {
//            this.session.getTransaction().rollback();
            this.session.getTransaction().commit();
            this.session.close();
        }
    }

    @Test
    void test() {
        Member scott = new Member();
        Member tiger = new Member();

        Feed feed1 = new Feed("title1", "content1", tiger);
        Feed feed2 = new Feed("title2", "content2", scott);

        scott.setMemberId("scott1234");
        scott.setMemberName("scott tiger");
        scott.setBookmarks(
                List.of(feed1, feed2)
        );

        tiger.setMemberId("tiger4321");
        tiger.setMemberName("tiger");
        tiger.setFeeds(List.of(feed1));
        tiger.setBookmarks(
                List.of(feed1, feed2)
        );

        this.session.persist(scott);
        this.session.flush();
        this.session.persist(tiger);
    }
}
