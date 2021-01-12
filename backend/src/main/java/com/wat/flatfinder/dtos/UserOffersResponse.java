package com.wat.flatfinder.dtos;

import com.wat.flatfinder.entities.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOffersResponse {
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
    private String note;
    private int id;
}
