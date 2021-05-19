package com.wat.flatfinder.entities;

import lombok.Data;

import javax.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(name = "user_offers")
public class UserOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
