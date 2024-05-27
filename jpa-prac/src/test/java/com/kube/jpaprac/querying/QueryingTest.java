package com.kube.jpaprac.querying;

import com.kube.jpaprac.config.HibernateConfig;
import com.kube.jpaprac.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringJUnitConfig(classes = HibernateConfig.class)
public class QueryingTest {

    @Autowired
    SessionFactory sessionFactory;

    Session session;

    List<Member> testSamples;

    @BeforeEach
    void init() {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        initData();
    }

    @AfterEach
    void afterEach() {
        if (this.session != null) {
            this.session.getTransaction().rollback();
            this.session.close();
        }
    }

    private void initData() {
        this.testSamples = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Member member = new Member();
            member.setMemberId("member" + (i + 1));
            member.setMemberName("name" + (i + 1));
            this.testSamples.add(member);
            this.session.persist(member);
        }
    }

    @DisplayName("FROM 테스트")
    @Test
    void from() {
        List<Member> findMembers = this.session.createQuery("FROM Member", Member.class) // Table명이 아닌 Entity class명
                .getResultList();
        log.info("findMembers={}", findMembers);
        assertThat(findMembers).contains(this.testSamples.toArray(new Member[0]));
    }

    @DisplayName("WHERE 테스트")
    @Test
    void where() {
        Object singleResult = this.session.createQuery("FROM Member m WHERE m.memberId = :memberId", Member.class)
                .setParameter("memberId", "member1")
                .getSingleResult();

        assertThat(singleResult).isNotNull();
        assertThat(singleResult).isEqualTo(this.testSamples.get(0));
    }
}
