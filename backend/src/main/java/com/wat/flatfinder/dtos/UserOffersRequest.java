package com.wat.flatfinder.dtos;

import com.wat.flatfinder.entities.Offer;
import com.wat.flatfinder.entities.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class UserOffersRequest {
    private int user_id;
    private int offer_id;
    private String note;
}
