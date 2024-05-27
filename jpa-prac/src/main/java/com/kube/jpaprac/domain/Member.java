package com.kube.jpaprac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
