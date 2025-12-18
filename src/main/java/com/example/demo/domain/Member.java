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

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "name")
    private String name;

    @Override
    public String toString(){
        return this.id+", "+this.loginId+", "+this.pass+", "+this.name;
    };
}
