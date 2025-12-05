package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="like_id")
    private Likes like;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "pass", nullable = false, unique = true)
    private String pass;

    @Column(name = "name")
    private String name;
}
