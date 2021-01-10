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

    @Column(name="username", unique = true)
    private String username;

    @Column(name="authority", unique = true)
    private String authority;
}
