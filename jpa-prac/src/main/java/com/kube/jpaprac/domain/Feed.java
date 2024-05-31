package com.kube.jpaprac.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bookmark",
            joinColumns = @JoinColumn(name = "feed_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> bookmarkedMembers;

    public Feed(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
