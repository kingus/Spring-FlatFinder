package com.wat.flatfinder.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class OfferRequest {

    private String id;
    private String district;
    private float area;
    private String img_url;
    private double latitude;
    private double longitude;
    private String offer_url;
    private float price;
    private String rooms;
    private String source;
    private String source_id;
    private String title;
    private LocalDateTime startDttm;
    private LocalDateTime endDttm;
}
