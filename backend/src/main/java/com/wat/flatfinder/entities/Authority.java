package com.wat.flatfinder.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="authority")
    private String authority;
}
