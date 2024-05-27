package com.kube.jpaprac.hibernate;

import com.kube.jpaprac.config.HibernateConfig;
import com.kube.jpaprac.domain.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(classes = HibernateConfig.class)
public class LocalDateTest {

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
            this.session.getTransaction().rollback();
            this.session.close();
        }
    }

    @Test
    void withLocalDate() {
        Member member = new Member();
        member.setMemberId("member1");
        member.setMemberName("memberName");
        member.setRegDate(LocalDate.now());
        this.session.persist(member);

        Member findMember = this.session.find(Member.class, "member1");

        assertThat(findMember.getRegDate()).isEqualTo(member.getRegDate());
    }
}
