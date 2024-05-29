package com.kube.jpaprac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "reg_date")
    private LocalDate regDate;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
//            , mappedBy = "writer"
    )
    @JoinColumn(name = "writer")
    private List<Feed> feeds;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bookmark",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "feed_id")
    )
    private List<Feed> bookmarks;
}
