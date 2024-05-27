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

import java.time.LocalDate;
import java.util.List;

@SpringJUnitConfig(classes = HibernateConfig.class)
public class TestOneToManyBidirectional {

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
        Member member = new Member();
        member.setMemberId("scott1234");
        member.setMemberName("scott");
        member.setRegDate(LocalDate.now());
        member.setFeeds(List.of(
                new Feed("title1", "content1", member),
                new Feed("title2", "content2", member),
                new Feed("title3", "content3", member)
        ));

        this.session.persist(member);
    }
}
