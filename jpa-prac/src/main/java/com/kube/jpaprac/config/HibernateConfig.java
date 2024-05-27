package com.kube.jpaprac.config;

import com.kube.jpaprac.domain.Feed;
import com.kube.jpaprac.domain.Member;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure("hibernate/hibernate-config.xml")
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(Feed.class)
                .buildSessionFactory();
    }
}
