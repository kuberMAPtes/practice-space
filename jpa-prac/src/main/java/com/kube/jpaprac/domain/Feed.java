package com.kube.jpaprac.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long feedId;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer")
    private Member writer;

    public Feed(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
