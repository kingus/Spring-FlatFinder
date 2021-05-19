package com.wat.flatfinder.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferAllResponse {
    private int id;
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
    private boolean isFavourite;
}
