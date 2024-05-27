package com.kube.jpaprac.hibernate;

import com.kube.jpaprac.config.HibernateConfig;
import com.kube.jpaprac.domain.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = HibernateConfig.class)
public class TestOneSessionMultipleTransaction {

    @Autowired
    SessionFactory sessionFactory;

    Session session;

    @BeforeEach
    void init() {
        this.session = this.sessionFactory.openSession();
    }

    @AfterEach
    void afterEach() {
        if (this.session != null) {
            this.session.close();
        }
    }

    @Test
    void test() {
        Transaction transaction1 = this.session.beginTransaction();
        Member member1 = new Member();
        member1.setMemberId("id1");
        member1.setMemberName("name1");
        this.session.persist(member1);

        transaction1.rollback();

        Transaction transaction2 = this.session.beginTransaction();
        Member member2 = new Member();
        member2.setMemberId("id2");
        member2.setMemberName("name2");
        this.session.persist(member2);
        transaction2.rollback();
    }
}
