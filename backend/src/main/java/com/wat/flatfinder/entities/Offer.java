package com.wat.flatfinder.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name="offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="district")
    private String district;

    @Column(name="area")
    private float area;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "offer_url")
    private String offerUrl;

    @Column(name = "price")
    private float price;

    @Column(name = "rooms")
    private String rooms;

    @Column(name = "source")
    private String source;

    @Column(name="source_id", unique = true)
    private String sourceId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_dttm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDttm;

    @Column(name = "end_dttm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDttm;

    @OneToMany(mappedBy = "id")
    private List<UserOffers> userOffers;


}