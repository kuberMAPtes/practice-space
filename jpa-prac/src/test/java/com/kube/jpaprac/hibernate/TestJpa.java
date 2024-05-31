package com.kube.jpaprac.hibernate;

import com.kube.jpaprac.domain.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestJpa {

    SessionFactory sessionFactory;
    Session session;

    @BeforeEach
    void init() {
        this.sessionFactory = new Configuration()
                .configure("hibernate/hibernate-config.xml")
                .addAnnotatedClass(Member.class)
                .buildSessionFactory();
        this.session = sessionFactory.openSession();
    }

    @AfterEach
    void afterEach() {
        if (this.session != null) {
            Transaction transaction = this.session.getTransaction();
            transaction.rollback();
            this.session.close();
        }
    }

    @Test
    void test() {
        session.beginTransaction();

        Member member = new Member();
        member.setMemberId("member1");
        member.setMemberName("name1");
        session.persist(member);
    }

    @Test
    void select() {
        this.session.beginTransaction();

        Member member = new Member();
        member.setMemberId("member1");
        member.setMemberName("name1");

        this.session.persist(member);

        Member findMember = this.session.find(Member.class, "member1");
        assertThat(findMember).isEqualTo(member);
    }
}